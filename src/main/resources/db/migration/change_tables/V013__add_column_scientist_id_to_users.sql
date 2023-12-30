ALTER TABLE users
    ADD COLUMN scientist_id BIGINT REFERENCES scientists(scientist_id);

ALTER TABLE users
    ALTER COLUMN scientist_id DROP NOT NULL;
