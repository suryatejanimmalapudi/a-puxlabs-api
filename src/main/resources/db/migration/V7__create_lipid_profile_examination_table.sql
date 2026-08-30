CREATE TABLE lipid_profile_examination
(
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    sample_type VARCHAR(100),

    total_cholesterol DOUBLE PRECISION,
    triglycerides DOUBLE PRECISION,
    hdl_cholesterol DOUBLE PRECISION,
    ldl_cholesterol DOUBLE PRECISION,
    vldl_cholesterol DOUBLE PRECISION,

    ldl_hdl_ratio DOUBLE PRECISION,
    total_cholesterol_hdl_ratio DOUBLE PRECISION,

    remarks VARCHAR(1000),
    pathologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_lipid_profile_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);