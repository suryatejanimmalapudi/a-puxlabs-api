CREATE TABLE report_download_rate_limit (
    rate_key VARCHAR(200) PRIMARY KEY,
    window_started_at TIMESTAMP NOT NULL,
    request_count INTEGER NOT NULL
);

CREATE INDEX idx_report_download_rate_limit_window
    ON report_download_rate_limit(window_started_at);
