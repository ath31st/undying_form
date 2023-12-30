ALTER TABLE scientists
    ADD COLUMN specialization_id INTEGER REFERENCES specializations (specialization_id);

ALTER TABLE scientists
    ALTER COLUMN specialization_id DROP NOT NULL;