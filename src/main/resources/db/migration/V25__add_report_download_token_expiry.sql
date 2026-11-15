ALTER TABLE registration
    ADD COLUMN report_download_token_issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN report_download_token_expires_at TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP + INTERVAL '30 days');
