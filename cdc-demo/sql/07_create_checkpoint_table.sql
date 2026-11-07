
-- ============================================================
-- File: 07_create_checkpoint_table.sql
-- Purpose: Create CDC connector checkpoint table
-- Database: cdc_destination
-- ============================================================

CREATE TABLE IF NOT EXISTS cdc_checkpoint (
    connector_name VARCHAR(100) PRIMARY KEY,
    last_processed_event_id BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Initialize the checkpoint for our CDC connector.
INSERT INTO cdc_checkpoint (
    connector_name,
    last_processed_event_id
)
VALUES (
    'employee-cdc-connector',
    0
)
ON CONFLICT (connector_name) DO NOTHING;

-- Verify checkpoint initialization.
SELECT *
FROM cdc_checkpoint
WHERE connector_name = 'employee-cdc-connector';

