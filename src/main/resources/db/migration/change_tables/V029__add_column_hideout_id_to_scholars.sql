ALTER TABLE scholars
    ADD COLUMN hideout_id BIGINT REFERENCES hideouts (hideout_id);