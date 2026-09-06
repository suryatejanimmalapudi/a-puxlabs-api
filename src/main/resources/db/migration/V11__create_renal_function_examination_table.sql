CREATE TABLE renal_function_examination (
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    test_type VARCHAR(30) NOT NULL,

    result_value DOUBLE PRECISION NOT NULL,

    sample_type VARCHAR(255),

    reference_range VARCHAR(255),

    unit VARCHAR(50),

    remarks VARCHAR(1000),

    pathologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_renal_function_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
);
