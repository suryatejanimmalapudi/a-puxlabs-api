ALTER TABLE registration ADD COLUMN barcode VARCHAR(100);
UPDATE registration SET barcode = 'REG-' || id WHERE barcode IS NULL;
CREATE UNIQUE INDEX ux_registration_barcode ON registration(barcode);

-- Existing test-request barcodes remain available for historical compatibility;
-- new printed/scanned barcodes belong to registration instead.

-- Existing data was created with one barcode per test. Keep those legacy values
-- for historical lookup; new orders use the order barcode above.
