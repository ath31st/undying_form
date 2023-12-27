ALTER TABLE scientists
    ADD COLUMN education_id SERIAL REFERENCES education (education_id);