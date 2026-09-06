CREATE TABLE urine_routine_examination
(
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    sample_type VARCHAR(100),

    -- Physical examination
    colour VARCHAR(100),
    deposit VARCHAR(255),
    appearance VARCHAR(100),

    ph DOUBLE PRECISION,
    specific_gravity DOUBLE PRECISION,

    quantity VARCHAR(100),

    -- Chemical examination
    urine_protein VARCHAR(100),
    bile_salt VARCHAR(100),
    urine_glucose VARCHAR(100),
    urine_ketones VARCHAR(100),
    bile_pigment VARCHAR(100),
    occult_blood VARCHAR(100),

    -- Microscopic examination
    rbcs VARCHAR(100),
    pus_cells VARCHAR(100),
    epithelial_cells VARCHAR(100),
    crystals VARCHAR(255),
    casts VARCHAR(255),
    amorphous_deposit VARCHAR(255),
    bacteria VARCHAR(255),
    trichomonas_vaginalis VARCHAR(255),
    yeast_cells VARCHAR(255),

    remarks VARCHAR(1000),
    pathologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_urine_routine_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);