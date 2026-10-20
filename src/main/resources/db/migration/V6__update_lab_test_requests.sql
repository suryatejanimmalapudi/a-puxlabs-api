UPDATE lab_test_requests
SET status = 'PENDING_COLLECTION'
WHERE status = 'PENDING';