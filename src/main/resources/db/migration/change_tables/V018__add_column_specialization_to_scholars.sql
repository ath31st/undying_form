ALTER TABLE scholars
    ADD COLUMN specialization_id INTEGER REFERENCES specializations (specialization_id);

ALTER TABLE scholars
    ALTER COLUMN specialization_id DROP NOT NULL;