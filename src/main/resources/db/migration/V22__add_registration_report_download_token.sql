CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE registration
    ADD COLUMN report_download_token UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX uk_registration_report_download_token
    ON registration(report_download_token);
