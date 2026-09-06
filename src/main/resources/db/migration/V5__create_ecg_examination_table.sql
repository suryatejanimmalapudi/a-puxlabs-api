CREATE TABLE ecg_examination
(
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    heart_rate_bpm INTEGER,
    pr_interval_ms INTEGER,
    p_duration_ms INTEGER,
    qrs_duration_ms INTEGER,
    t_duration_ms INTEGER,
    qt_interval_ms INTEGER,
    qtc_interval_ms INTEGER,

    p_axis_deg DOUBLE PRECISION,
    qrs_axis_deg DOUBLE PRECISION,
    t_axis_deg DOUBLE PRECISION,

    r_v5_mv DOUBLE PRECISION,
    s_v1_mv DOUBLE PRECISION,
    r_v5_plus_s_v1_mv DOUBLE PRECISION,

    machine_conclusion VARCHAR(1000),
    physician_impression VARCHAR(1000),

    physician_name VARCHAR(255),
    physician_confirmed BOOLEAN,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_ecg_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);