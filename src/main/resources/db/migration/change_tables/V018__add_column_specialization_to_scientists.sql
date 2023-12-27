ALTER TABLE scientists
    ADD COLUMN specialization_id SERIAL REFERENCES specializations (specialization_id);