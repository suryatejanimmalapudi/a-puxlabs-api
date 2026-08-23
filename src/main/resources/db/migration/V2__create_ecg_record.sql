CREATE TABLE ecg_records (
    id UUID PRIMARY KEY,
    patient_id VARCHAR(255) NOT NULL,
    file_url VARCHAR(1000) NOT NULL,
    original_file_name VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Adding an index here because your repository queries by patientId
CREATE INDEX idx_ecg_records_patient_id ON ecg_records(patient_id);