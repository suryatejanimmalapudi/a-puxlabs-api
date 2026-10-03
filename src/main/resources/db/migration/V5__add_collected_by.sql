ALTER TABLE lab_test_requests
ADD COLUMN collected_by VARCHAR(100),
ADD COLUMN collected_at TIMESTAMP,
ADD COLUMN rejection_reason VARCHAR(255);