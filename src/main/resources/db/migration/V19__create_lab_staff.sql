CREATE TABLE IF NOT EXISTS lab_staff (
    id BIGSERIAL PRIMARY KEY,
    lab_id VARCHAR(80) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    "role" VARCHAR(30) NOT NULL CHECK ("role" IN ('ADMIN', 'TECHNICIAN', 'RECEPTIONIST')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO lab_staff (lab_id, email, full_name, "role", is_active)
VALUES ('PILOT-LAB-001', 'suryatejan09@gmail.com', 'Surya Tejan', 'ADMIN', TRUE)
ON CONFLICT (email) DO UPDATE SET
    lab_id = EXCLUDED.lab_id,
    full_name = EXCLUDED.full_name,
    "role" = EXCLUDED."role",
    is_active = EXCLUDED.is_active;
