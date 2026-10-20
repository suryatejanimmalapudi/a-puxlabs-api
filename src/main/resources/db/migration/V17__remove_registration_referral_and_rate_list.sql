ALTER TABLE registration
    DROP COLUMN IF EXISTS referring_doctor_id,
    DROP COLUMN IF EXISTS rate_list_id;
