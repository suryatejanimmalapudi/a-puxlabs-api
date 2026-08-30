CREATE TABLE cbp_examination
(
    id BIGSERIAL PRIMARY KEY,

    registration_id BIGINT NOT NULL,

    examination_date_time TIMESTAMP,

    -- Basic haematology
    haemoglobin DOUBLE PRECISION,
    total_leukocyte_count INTEGER,

    -- Differential leukocyte count (%)
    neutrophils DOUBLE PRECISION,
    lymphocytes DOUBLE PRECISION,
    eosinophils DOUBLE PRECISION,
    monocytes DOUBLE PRECISION,
    basophils DOUBLE PRECISION,

    -- Absolute leukocyte counts
    absolute_neutrophils INTEGER,
    absolute_lymphocytes INTEGER,
    absolute_eosinophils INTEGER,
    absolute_monocytes INTEGER,

    -- RBC indices
    rbc_count DOUBLE PRECISION,
    hct DOUBLE PRECISION,
    mcv DOUBLE PRECISION,
    mch DOUBLE PRECISION,
    mchc DOUBLE PRECISION,
    rdw_cv DOUBLE PRECISION,
    rdw_sd DOUBLE PRECISION,

    -- Platelet indices
    platelet_count INTEGER,
    pct DOUBLE PRECISION,
    mpv DOUBLE PRECISION,
    pdw DOUBLE PRECISION,
    p_lcr DOUBLE PRECISION,
    p_lcc DOUBLE PRECISION,

    remarks VARCHAR(1000),
    pathologist_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_cbp_examination_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);