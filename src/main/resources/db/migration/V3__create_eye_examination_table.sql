CREATE TABLE eye_examination (
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date DATE,

    right_distance_sph DOUBLE PRECISION,
    right_distance_cyl DOUBLE PRECISION,
    right_distance_axis INTEGER,
    right_distance_va VARCHAR(50),

    left_distance_sph DOUBLE PRECISION,
    left_distance_cyl DOUBLE PRECISION,
    left_distance_axis INTEGER,
    left_distance_va VARCHAR(50),

    right_near_sph DOUBLE PRECISION,
    right_near_cyl DOUBLE PRECISION,
    right_near_axis INTEGER,
    right_near_va VARCHAR(50),

    left_near_sph DOUBLE PRECISION,
    left_near_cyl DOUBLE PRECISION,
    left_near_axis INTEGER,
    left_near_va VARCHAR(50),

    colour_vision VARCHAR(100),

    remarks TEXT,

    optometrist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_eye_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
);