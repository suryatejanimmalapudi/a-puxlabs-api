ALTER TABLE lab_test_requests
ADD COLUMN verified_by VARCHAR(100),
ADD COLUMN verified_at TIMESTAMP,
ADD COLUMN delivered_at TIMESTAMP;
