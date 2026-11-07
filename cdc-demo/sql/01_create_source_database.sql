
-- ============================================================
-- File: 01_create_source_database.sql
-- Purpose: Create the source database for CDC demonstration
-- ============================================================

-- Create the source database if it does not already exist.
SELECT 'CREATE DATABASE cdc_source'
WHERE NOT EXISTS (
    SELECT FROM pg_database
    WHERE datname = 'cdc_source'
)\gexec

-- Verify database creation.
SELECT datname
FROM pg_database
WHERE datname = 'cdc_source';

