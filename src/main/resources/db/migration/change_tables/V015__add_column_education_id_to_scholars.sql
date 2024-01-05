ALTER TABLE scholars
    ADD COLUMN education_id INTEGER REFERENCES education (education_id);