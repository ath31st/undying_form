ALTER TABLE scholars
    ADD COLUMN hideout_id BIGINT UNIQUE REFERENCES hideouts (hideout_id);