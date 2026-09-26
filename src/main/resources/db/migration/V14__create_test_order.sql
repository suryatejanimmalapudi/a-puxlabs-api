-- 1. Tests Master Catalog
-- Stores the available tests the clinic offers (e.g., CBP, LFT)
CREATE TABLE tests_master (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    department VARCHAR(100) NOT NULL,
    default_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tests_master_code ON tests_master(code);
CREATE INDEX idx_tests_master_dept ON tests_master(department);


-- 2. Lab Orders (The Front Desk / Billing View)
-- One record per patient transaction at the front desk
CREATE TABLE lab_orders (
    id SERIAL PRIMARY KEY,
    registration_id INTEGER NOT NULL,
    referring_doctor_name VARCHAR(255),

    registration_fee DECIMAL(10, 2) DEFAULT 0.00,
    total_discount DECIMAL(10, 2) DEFAULT 0.00,   -- NEW: Total discount applied to the whole order
    gross_amount DECIMAL(10, 2) NOT NULL,         -- NEW: Amount before discount
    net_amount DECIMAL(10, 2) NOT NULL,           -- CHANGED: Final amount to be paid (gross - discount)

    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lab_orders_registration
        FOREIGN KEY (registration_id)
        REFERENCES registration(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_lab_orders_registration ON lab_orders(registration_id);


-- 3. Lab Test Requests (The Laboratory Technician Queue)
-- Maps specific tests to a specific order. Drives the Pending Queue UI.
CREATE TABLE lab_test_requests (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    test_master_id INTEGER NOT NULL,

    barcode VARCHAR(100) UNIQUE,

    base_price DECIMAL(10, 2) NOT NULL,           -- NEW: The original price of the test (e.g., 100)
    discount_amount DECIMAL(10, 2) DEFAULT 0.00,  -- NEW: The discount given on this specific test (e.g., 50)
    price_charged DECIMAL(10, 2) NOT NULL,        -- The final price after discount (e.g., 50)

    priority VARCHAR(50) DEFAULT 'Routine',
    status VARCHAR(50) DEFAULT 'PENDING',

    result_data JSONB,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,

    CONSTRAINT fk_lab_test_requests_order
        FOREIGN KEY (order_id)
        REFERENCES lab_orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_lab_test_requests_test_master
        FOREIGN KEY (test_master_id)
        REFERENCES tests_master(id)
);

CREATE INDEX idx_lab_test_requests_status ON lab_test_requests(status);
CREATE INDEX idx_lab_test_requests_barcode ON lab_test_requests(barcode);
CREATE INDEX idx_lab_test_requests_order ON lab_test_requests(order_id);