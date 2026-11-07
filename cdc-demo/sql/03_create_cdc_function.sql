
-- ============================================================
-- File: 03_create_cdc_function.sql
-- Purpose: Create the CDC trigger function
-- Database: cdc_source
-- ============================================================

CREATE OR REPLACE FUNCTION capture_employee_changes()
RETURNS TRIGGER AS $$
BEGIN

    IF (TG_OP = 'INSERT') THEN

        INSERT INTO cdc_events (
            operation,
            record_id,
            changed_data
        )
        VALUES (
            'INSERT',
            NEW.id,
            to_jsonb(NEW)
        );

        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN

        INSERT INTO cdc_events (
            operation,
            record_id,
            changed_data
        )
        VALUES (
            'UPDATE',
            NEW.id,
            to_jsonb(NEW)
        );

        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN

        INSERT INTO cdc_events (
            operation,
            record_id,
            changed_data
        )
        VALUES (
            'DELETE',
            OLD.id,
            to_jsonb(OLD)
        );

        RETURN OLD;

    END IF;

    RETURN NULL;

END;
$$ LANGUAGE plpgsql;

-- Verify function creation.
SELECT routine_name
FROM information_schema.routines
WHERE routine_schema = 'public'
  AND routine_name = 'capture_employee_changes';

