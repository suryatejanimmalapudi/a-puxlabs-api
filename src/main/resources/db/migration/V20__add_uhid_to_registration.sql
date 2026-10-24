ALTER TABLE registration ADD COLUMN uhid VARCHAR(100);

CREATE UNIQUE INDEX uk_registration_uhid
    ON registration (uhid)
    WHERE uhid IS NOT NULL;
