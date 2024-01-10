ALTER TABLE users
    ADD COLUMN scholar_id BIGINT UNIQUE REFERENCES scholars (scholar_id);

ALTER TABLE users
    ALTER COLUMN scholar_id DROP NOT NULL;
