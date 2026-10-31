ALTER TABLE registration ADD COLUMN age INTEGER;

UPDATE registration
SET age = EXTRACT(YEAR FROM AGE(CURRENT_DATE, date_of_birth))::INTEGER
WHERE date_of_birth IS NOT NULL;

ALTER TABLE registration DROP COLUMN IF EXISTS date_of_birth;
