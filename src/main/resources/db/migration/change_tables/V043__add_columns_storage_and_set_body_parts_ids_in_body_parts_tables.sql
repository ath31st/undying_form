ALTER TABLE hands
    ADD COLUMN storage_id BIGINT UNIQUE REFERENCES storages (storage_id);
ALTER TABLE legs
    ADD COLUMN storage_id BIGINT UNIQUE REFERENCES storages (storage_id);
ALTER TABLE upper_bodies
    ADD COLUMN storage_id BIGINT UNIQUE REFERENCES storages (storage_id);
ALTER TABLE heads
    ADD COLUMN storage_id BIGINT UNIQUE REFERENCES storages (storage_id);

ALTER TABLE hands
    ADD COLUMN set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id);
ALTER TABLE legs
    ADD COLUMN set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id);
ALTER TABLE upper_bodies
    ADD COLUMN set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id);
ALTER TABLE heads
    ADD COLUMN set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id);
