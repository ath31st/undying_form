ALTER TABLE hideouts
    ADD COLUMN storage_id BIGINT NOT NULL UNIQUE REFERENCES storages (storage_id) DEFAULT 0;