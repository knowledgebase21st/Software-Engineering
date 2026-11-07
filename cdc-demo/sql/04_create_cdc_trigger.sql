
-- ============================================================
-- File: 04_create_cdc_trigger.sql
-- Purpose: Create the CDC trigger on employees table
-- Database: cdc_source
-- ============================================================

-- Remove the trigger if it already exists.
DROP TRIGGER IF EXISTS employee_changes_trigger
ON employees;

-- Create the CDC trigger.
CREATE TRIGGER employee_changes_trigger
AFTER INSERT OR UPDATE OR DELETE
ON employees
FOR EACH ROW
EXECUTE FUNCTION capture_employee_changes();

-- Verify trigger creation.
SELECT trigger_name, event_manipulation
FROM information_schema.triggers
WHERE event_object_table = 'employees'
ORDER BY event_manipulation;

