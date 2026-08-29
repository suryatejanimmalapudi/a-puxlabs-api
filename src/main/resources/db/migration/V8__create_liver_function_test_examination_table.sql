CREATE TABLE liver_function_test_examination
(
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    sample_type VARCHAR(100),

    -- Bilirubin
    bilirubin_total DOUBLE PRECISION,
    bilirubin_direct DOUBLE PRECISION,
    bilirubin_indirect DOUBLE PRECISION,

    -- Liver enzymes
    sgpt DOUBLE PRECISION,
    sgot DOUBLE PRECISION,
    sgot_sgpt_ratio DOUBLE PRECISION,

    alkaline_phosphatase DOUBLE PRECISION,
    gamma_glutamyl_transferase DOUBLE PRECISION,

    -- Proteins
    total_proteins DOUBLE PRECISION,
    albumin DOUBLE PRECISION,
    globulin DOUBLE PRECISION,
    albumin_globulin_ratio DOUBLE PRECISION,

    remarks VARCHAR(1000),
    pathologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_liver_function_test_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);