ALTER TABLE scientists
    ADD COLUMN hideout_id BIGSERIAL REFERENCES hideouts (hideout_id);