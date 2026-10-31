ALTER TABLE lab_orders ADD COLUMN IF NOT EXISTS accession_number VARCHAR(80);
CREATE UNIQUE INDEX IF NOT EXISTS uk_lab_orders_accession ON lab_orders(accession_number);
ALTER TABLE lab_orders ADD COLUMN IF NOT EXISTS payment_status VARCHAR(30) DEFAULT 'PENDING';
CREATE TABLE IF NOT EXISTS lab_configuration (
  config_key VARCHAR(80) PRIMARY KEY,
  config_value VARCHAR(255) NOT NULL
);
INSERT INTO lab_configuration(config_key, config_value)
VALUES ('registration_fee', '0') ON CONFLICT (config_key) DO NOTHING;
