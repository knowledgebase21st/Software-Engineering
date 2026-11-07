
-- ============================================================
-- File: 05_create_destination_database.sql
-- Purpose: Create the destination database for CDC
-- ============================================================

-- Create the destination database if it does not already exist.
SELECT 'CREATE DATABASE cdc_destination'
WHERE NOT EXISTS (
    SELECT FROM pg_database
    WHERE datname = 'cdc_destination'
)\gexec

-- Verify database creation.
SELECT datname
FROM pg_database
WHERE datname = 'cdc_destination';

