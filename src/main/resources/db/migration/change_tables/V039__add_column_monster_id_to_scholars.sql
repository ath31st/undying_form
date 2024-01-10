ALTER TABLE scholars
    ADD COLUMN monster_id BIGINT UNIQUE REFERENCES monsters (monster_id);