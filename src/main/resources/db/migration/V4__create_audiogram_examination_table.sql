CREATE TABLE audiogram (
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date DATE,

    right_125_hz INTEGER,
    right_250_hz INTEGER,
    right_500_hz INTEGER,
    right_1000_hz INTEGER,
    right_2000_hz INTEGER,
    right_4000_hz INTEGER,
    right_8000_hz INTEGER,

    left_125_hz INTEGER,
    left_250_hz INTEGER,
    left_500_hz INTEGER,
    left_1000_hz INTEGER,
    left_2000_hz INTEGER,
    left_4000_hz INTEGER,
    left_8000_hz INTEGER,

    impression TEXT,

    audiologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_audiogram_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
);