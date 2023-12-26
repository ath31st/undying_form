ALTER TABLE scientists
    ADD COLUMN education_id INT REFERENCES education (education_id);