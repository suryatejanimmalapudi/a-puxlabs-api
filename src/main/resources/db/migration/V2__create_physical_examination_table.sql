CREATE TABLE physical_examination (
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date DATE,

    height DOUBLE PRECISION,

    weight DOUBLE PRECISION,

    pulse_rate INTEGER,

    blood_pressure VARCHAR(50),

    temperature DOUBLE PRECISION,

    present_complaints TEXT,

    past_history TEXT,

    cardiovascular_system TEXT,

    respiratory_system TEXT,

    abdomen TEXT,

    central_nervous_system TEXT,

    skin_examination TEXT,

    epilepsy TEXT,

    medical_certificate TEXT,

    doctor_name VARCHAR(255),

    doctor_registration_number VARCHAR(100),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_physical_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
);