DROP INDEX IF EXISTS uk_lab_test_requests_download_token;

ALTER TABLE lab_test_requests
    DROP COLUMN IF EXISTS download_token;
