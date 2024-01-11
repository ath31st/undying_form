ALTER TABLE hideouts
    ADD COLUMN storage_id BIGINT UNIQUE REFERENCES storages (storage_id);