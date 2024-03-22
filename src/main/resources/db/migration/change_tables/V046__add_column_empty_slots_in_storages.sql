ALTER TABLE storages
    ADD COLUMN empty_slots INTEGER;

UPDATE storages s
SET empty_slots = capacity - (SELECT COUNT(*)
                              FROM body_parts bp
                              WHERE bp.storage_id = s.storage_id);

ALTER TABLE storages
    ADD CONSTRAINT check_empty_slots_non_negative CHECK (empty_slots >= 0);