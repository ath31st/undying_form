ALTER TABLE scientists
    ADD COLUMN hideout_id BIGINT REFERENCES hideouts (hideout_id);