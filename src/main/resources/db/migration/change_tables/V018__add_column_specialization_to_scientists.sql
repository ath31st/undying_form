ALTER TABLE scientists
    ADD COLUMN specialization_id SERIAL REFERENCES specializations (specialization_id);

ALTER TABLE scientists
    ALTER COLUMN specialization_id DROP NOT NULL;