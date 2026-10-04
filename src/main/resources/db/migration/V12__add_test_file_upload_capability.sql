ALTER TABLE tests_master
    ADD COLUMN file_upload_allowed BOOLEAN NOT NULL DEFAULT FALSE;

-- ECG is the first test type that accepts a report file.
UPDATE tests_master
SET file_upload_allowed = TRUE
WHERE UPPER(code) = 'ECG';
