CREATE TABLE sets_body_parts
(
    set_body_parts_id BIGSERIAL PRIMARY KEY,
    body_parts_count  INTEGER NOT NULL CHECK (body_parts_count >= 0),
    set_is_completed  BOOLEAN NOT NULL,
    bonus_set         INTEGER NOT NULL
);