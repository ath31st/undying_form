ALTER TABLE users
    ADD COLUMN scientist_id BIGSERIAL REFERENCES scientists(scientist_id);

ALTER TABLE users
    ALTER COLUMN scientist_id DROP NOT NULL;
