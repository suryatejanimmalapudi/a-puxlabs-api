ALTER TABLE lab_test_requests
    ADD COLUMN package_id BIGINT;

ALTER TABLE lab_test_requests
    ADD CONSTRAINT fk_lab_test_requests_package
        FOREIGN KEY (package_id)
        REFERENCES test_package(id);

CREATE INDEX idx_lab_test_requests_package ON lab_test_requests(package_id);
