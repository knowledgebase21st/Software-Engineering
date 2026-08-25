-- ============================================================
-- Chapter 4 - SQL Injection
-- Section 4.6.1 - Vulnerable JDBC Example
-- ============================================================

-- Create the table used by the example.
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL
);

-- Insert sample users.
INSERT INTO users (username, password, full_name)
VALUES
    ('alice', 'alice123', 'Alice Johnson'),
    ('bob', 'bob123', 'Bob Smith'),
    ('charlie', 'charlie123', 'Charlie Brown')
ON CONFLICT (username) DO NOTHING;
