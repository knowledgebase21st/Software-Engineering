
package com.clockwise.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CDCConnector {



    private static final Properties CONFIG = loadConfiguration();
    
    private static final String SOURCE_URL =
            CONFIG.getProperty("source.db.url");
    
    private static final String DESTINATION_URL =
            CONFIG.getProperty("destination.db.url");
    
    private static final String DB_USER =
            CONFIG.getProperty("db.username", "postgres");
    
    private static final String DB_PASSWORD =
            System.getenv("DB_PASSWORD");
    
    private static final int BATCH_SIZE =
            Integer.parseInt(
                    CONFIG.getProperty("cdc.batch.size", "1")
            );

    private static final int CDC_FETCH_SIZE =
        Integer.parseInt(CONFIG.getProperty("cdc.fetch.size", "100"));

    
    private static final long POLLING_INTERVAL_MS =
            Long.parseLong(
                    CONFIG.getProperty("cdc.polling.interval.ms", "2000")
            );
    
    private static final int MAX_RETRIES =
            Integer.parseInt(
                    CONFIG.getProperty("cdc.max.retries", "3")
            );
    
    private static final long INITIAL_RETRY_DELAY_MS =
            Long.parseLong(
                    CONFIG.getProperty("cdc.retry.delay.ms", "1000")
            );
    
    private static final String CONNECTOR_NAME =
            "employee-cdc-connector";

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private static volatile boolean running = true;

    private static final long RECONNECT_DELAY_MS =
        Long.parseLong(
                CONFIG.getProperty("cdc.reconnect.delay.ms", "5000")
        );




    public static void main(String[] args) {
    
        if (DB_PASSWORD == null) {
            throw new IllegalStateException(
                    "Please set the DB_PASSWORD environment variable."
            );
        }
    
	if (BATCH_SIZE < 1 || CDC_FETCH_SIZE < 1
        || POLLING_INTERVAL_MS < 1
        || MAX_RETRIES < 1 || INITIAL_RETRY_DELAY_MS < 1
        || RECONNECT_DELAY_MS < 1) {
                throw new IllegalArgumentException(
                "CDC configuration values must be positive.");
        }
    
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            System.out.println("\nShutdown requested...");
        }));
    
        System.out.println("Starting CDC Connector...");
        System.out.println("CDC batch size: " + BATCH_SIZE);
    
        while (running) {
    
            try (
                Connection sourceConnection =
                        DriverManager.getConnection(
                                SOURCE_URL, DB_USER, DB_PASSWORD);
    
                Connection destinationConnection =
                        DriverManager.getConnection(
                                DESTINATION_URL, DB_USER, DB_PASSWORD)
            ) {
    
                System.out.println(
                        "Connected to source database: "
                                + sourceConnection.getCatalog()
                );
    
                System.out.println(
                        "Connected to destination database: "
                                + destinationConnection.getCatalog()
                );
    
                // This method reads the latest checkpoint on every
                // new connection session.
                runContinuousPolling(
                        sourceConnection,
                        destinationConnection
                );
    
            } catch (SQLException e) {
    
                if (!running) {
                    break;
                }
    
                if (!isConnectionFailure(e)) {
                    System.err.println(
                            "Non-connection database error. Stopping connector:"
                    );
                    e.printStackTrace();
                    break;
                }
    
                System.err.println(
                        "Database connection lost. Reconnecting in "
                                + RECONNECT_DELAY_MS + " ms..."
                );
    
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
    
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
    
            } catch (Exception e) {
    
                System.err.println("CDC Connector failed:");
                e.printStackTrace();
                break;
            }
        }
    
        System.out.println("CDC Connector stopped.");
    }



    private static boolean isConnectionFailure(SQLException e) {
    
        SQLException current = e;
    
        while (current != null) {
    
            String sqlState = current.getSQLState();
    
            if (sqlState != null &&
                    (sqlState.startsWith("08")
                    || sqlState.equals("57P01")
                    || sqlState.equals("57P02")
                    || sqlState.equals("57P03"))) {
    
                return true;
            }
    
            current = current.getNextException();
        }
    
        return false;
    }



    private static void runContinuousPolling(
            Connection sourceConnection,
            Connection destinationConnection) throws Exception {
    
        long lastProcessedEventId =
                getLastProcessedEventId(destinationConnection);
    
        System.out.println(
                "\nStarting continuous CDC polling..."
        );
    
        System.out.println(
                "Last processed event ID: " + lastProcessedEventId
        );
    
	System.out.println(
           "Polling interval: " + POLLING_INTERVAL_MS + " ms");
        System.out.println("Press Ctrl+C to stop the connector.\n");
    
        while (running && !Thread.currentThread().isInterrupted()) {
    
            lastProcessedEventId = processAvailableEvents(
                    sourceConnection,
                    destinationConnection,
                    lastProcessedEventId
            );
    
            try {
                Thread.sleep(POLLING_INTERVAL_MS);
    
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    
        System.out.println("CDC Connector stopped.");
    }
    


    private static long processAvailableEvents(
            Connection sourceConnection,
            Connection destinationConnection,
            long lastProcessedEventId) throws Exception {
    
        String sql = """
                SELECT event_id, operation, record_id, changed_data
                FROM cdc_events
                WHERE event_id > ?
                ORDER BY event_id
                LIMIT ?
                """;
    
        while (true) {
    
            List<CDCEvent> events = new ArrayList<>();
    
            try (PreparedStatement statement =
                         sourceConnection.prepareStatement(sql)) {
    
                statement.setLong(1, lastProcessedEventId);
                statement.setInt(2, CDC_FETCH_SIZE);
    
                try (ResultSet resultSet = statement.executeQuery()) {
    
                    while (resultSet.next()) {
    
                        long eventId = resultSet.getLong("event_id");
                        String operation = resultSet.getString("operation");
                        int recordId = resultSet.getInt("record_id");
    
                        String changedData =
                                resultSet.getString("changed_data");
    
                        JsonNode employeeData =
                                OBJECT_MAPPER.readTree(changedData);
    
                        events.add(new CDCEvent(
                                eventId,
                                operation,
                                recordId,
                                employeeData
                        ));
                    }
                }
            }
    
            // No more events are currently available.
            if (events.isEmpty()) {
                break;
            }
    
            // Process the fetched events in configured batch sizes.
            for (int i = 0; i < events.size(); i += BATCH_SIZE) {
    
                int endIndex = Math.min(
                        i + BATCH_SIZE,
                        events.size()
                );
    
                List<CDCEvent> batch =
                        events.subList(i, endIndex);
    
                processBatchWithRetry(
                        destinationConnection,
                        batch
                );
    
                lastProcessedEventId =
                        batch.get(batch.size() - 1).eventId();
            }
    
            // Continue fetching the next page.
        }
    
        return lastProcessedEventId;
    }


    

    private static long getLastProcessedEventId(
            Connection connection) throws SQLException {

        String sql = """
                SELECT last_processed_event_id
                FROM cdc_checkpoint
                WHERE connector_name = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, CONNECTOR_NAME);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getLong(
                            "last_processed_event_id");
                }

                throw new SQLException(
                        "CDC checkpoint not found: " + CONNECTOR_NAME
                );
            }
        }
    }

    private static void applyChange(
            Connection connection,
            String operation,
            JsonNode employeeData) throws SQLException {

        int employeeId =
                employeeData.get("id").asInt();

        switch (operation) {

            case "INSERT" -> {

                String sql = """
                        INSERT INTO employees
                        (id, name, department, salary)
                        VALUES (?, ?, ?, ?)
                        """;

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setInt(1, employeeId);
                    statement.setString(
                            2, employeeData.get("name").asText());

                    setDepartment(statement, 3, employeeData);
                    setSalary(statement, 4, employeeData);

                    statement.executeUpdate();
                }
            }

            case "UPDATE" -> {

                String sql = """
                        UPDATE employees
                        SET name = ?, department = ?, salary = ?
                        WHERE id = ?
                        """;

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setString(
                            1, employeeData.get("name").asText());

                    setDepartment(statement, 2, employeeData);
                    setSalary(statement, 3, employeeData);

                    statement.setInt(4, employeeId);

                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated != 1) {
                        throw new SQLException(
                                "Expected to update employee "
                                        + employeeId
                        );
                    }
                }
            }

            case "DELETE" -> {

                String sql =
                        "DELETE FROM employees WHERE id = ?";

                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {

                    statement.setInt(1, employeeId);

                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted != 1) {
                        throw new SQLException(
                                "Expected to delete employee "
                                        + employeeId
                        );
                    }
                }
            }

            default -> throw new SQLException(
                    "Unsupported CDC operation: " + operation
            );
        }
    }

    private static void setDepartment(
            PreparedStatement statement,
            int parameterIndex,
            JsonNode employeeData) throws SQLException {

        JsonNode department =
                employeeData.get("department");

        if (department == null || department.isNull()) {
            statement.setNull(parameterIndex, Types.VARCHAR);
        } else {
            statement.setString(
                    parameterIndex, department.asText());
        }
    }

    private static void setSalary(
            PreparedStatement statement,
            int parameterIndex,
            JsonNode employeeData) throws SQLException {

        JsonNode salary = employeeData.get("salary");

        if (salary == null || salary.isNull()) {
            statement.setNull(parameterIndex, Types.NUMERIC);
        } else {
            BigDecimal value = salary.decimalValue();
            statement.setBigDecimal(parameterIndex, value);
        }
    }

    private static void updateCheckpoint(
            Connection connection,
            long eventId) throws SQLException {

        String sql = """
                UPDATE cdc_checkpoint
                SET last_processed_event_id = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE connector_name = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, eventId);
            statement.setString(2, CONNECTOR_NAME);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated != 1) {
                throw new SQLException(
                        "Failed to update CDC checkpoint."
                );
            }
        }
    }

    
    
    private record CDCEvent(
        long eventId,
        String operation,
        int recordId,
        JsonNode employeeData) {
    }
    
        
    
    private static void processBatchWithRetry(
            Connection destinationConnection,
            List<CDCEvent> events) throws SQLException {
    
        if (events.isEmpty()) {
            return;
        }
    
        boolean originalAutoCommit =
                destinationConnection.getAutoCommit();
    
        boolean transactionResolved = false;
        boolean connectionClosed = false;
        Throwable failure = null;
    
        try {
            destinationConnection.setAutoCommit(false);
    
	    for (int attempt = 1; attempt <= MAX_RETRIES + 1; attempt++) {
    
                transactionResolved = false;
    
                try {
                    for (CDCEvent event : events) {
    
                        applyChange(
                                destinationConnection,
                                event.operation(),
                                event.employeeData()
                        );
                    }
    
                    long lastEventId =
                            events.get(events.size() - 1).eventId();
    
                    updateCheckpoint(destinationConnection, lastEventId);
    
                    destinationConnection.commit();
    
                    transactionResolved = true;
    
                    // Log only after successful commit.
                    for (CDCEvent event : events) {
                        System.out.println(
                                "Applied event " + event.eventId()
                                        + " : " + event.operation()
                                        + " employee ID " + event.recordId()
                        );
                    }
    
                    System.out.println(
                            "Batch committed. Last event ID: " + lastEventId
                    );
    
                    return;
    
                } catch (SQLException | RuntimeException e) {
    
                    try {
                        destinationConnection.rollback();
                        transactionResolved = true;
    
                    } catch (SQLException rollbackError) {
    
                        e.addSuppressed(rollbackError);
    
                        // Do not reuse a connection with an unresolved transaction.
                        try {
                            destinationConnection.close();
                            connectionClosed = true;
    
                        } catch (SQLException closeError) {
                            e.addSuppressed(closeError);
                        }
    
                        throw e;
                    }
    
                    // Runtime exceptions are not automatically retryable.
                    if (!(e instanceof SQLException sqlException)) {
                        throw e;
                    }
    
                    // Let the outer connection-recovery loop handle connection failures.
                    if (isConnectionFailure(sqlException)) {
                        throw sqlException;
                    }
    
                    if (!isRetryableTransactionError(sqlException)
                            || attempt == MAX_RETRIES + 1) {
                        throw sqlException;
                    }
    
                    long delay =
                            INITIAL_RETRY_DELAY_MS * (1L << (attempt - 1));
    
                    System.err.println(
                            "Transient transaction error processing batch. "
                                    + "Attempt " + attempt
                                    + " failed. Retrying in "
                                    + delay + " ms."
                    );
    
                    try {
                        Thread.sleep(delay);
    
                    } catch (InterruptedException interrupted) {
    
                        Thread.currentThread().interrupt();
    
                        throw new SQLException(
                                "Interrupted while retrying CDC batch.",
                                interrupted
                        );
                    }
                }
            }
    
        } catch (SQLException | RuntimeException e) {
    
            failure = e;
            throw e;
    
        } finally {
    
            if (!connectionClosed && transactionResolved) {
    
                try {
                    destinationConnection.setAutoCommit(originalAutoCommit);
    
                } catch (SQLException cleanupError) {
    
                    if (failure != null) {
                        failure.addSuppressed(cleanupError);
                    } else {
                        throw cleanupError;
                    }
                }
    
            } else if (!connectionClosed) {
    
                // An unresolved transaction must not be committed accidentally.
                try {
                    destinationConnection.close();
    
                } catch (SQLException cleanupError) {
    
                    if (failure != null) {
                        failure.addSuppressed(cleanupError);
                    } else {
                        throw cleanupError;
                    }
                }
            }
        }
    }

    private static Properties loadConfiguration() {
    
        Properties properties = new Properties();
    
        try (FileInputStream input =
                     new FileInputStream("application.properties")) {
    
            properties.load(input);
    
            System.out.println(
                    "Configuration loaded from application.properties"
            );
    
        } catch (IOException e) {
    
            throw new IllegalStateException(
                    "Unable to load application.properties", e
            );
        }
    
        return properties;
    }


    private static boolean isRetryableTransactionError(SQLException e) {
    
        String sqlState = e.getSQLState();
    
        if (sqlState == null) {
            return false;
        }
    
        return sqlState.startsWith("40")  // Transaction rollback
                || sqlState.equals("55P03"); // Lock not available
    }

}

