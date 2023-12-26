ALTER TABLE scientists
    ADD COLUMN specialization_id INT REFERENCES specializations (specialization_id);