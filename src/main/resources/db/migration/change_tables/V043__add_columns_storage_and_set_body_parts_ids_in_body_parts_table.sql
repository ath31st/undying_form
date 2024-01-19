ALTER TABLE body_parts
    ADD COLUMN storage_id BIGINT REFERENCES storages (storage_id);

ALTER TABLE body_parts
    ADD COLUMN scholar_id BIGINT REFERENCES scholars (scholar_id);

ALTER TABLE body_parts
    ADD COLUMN set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id);