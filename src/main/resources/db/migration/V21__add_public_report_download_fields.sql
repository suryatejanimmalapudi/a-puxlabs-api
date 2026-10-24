CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE lab_test_requests
    ADD COLUMN download_token UUID NOT NULL DEFAULT gen_random_uuid(),
    ADD COLUMN report_file_path VARCHAR(500);

CREATE UNIQUE INDEX uk_lab_test_requests_download_token
    ON lab_test_requests(download_token);
