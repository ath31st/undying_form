CREATE TABLE sets_body_parts
(
    set_body_parts_id BIGSERIAL PRIMARY KEY,
    body_parts_count  INTEGER NOT NULL CHECK (body_parts_count >= 0),
    set_is_completed  BOOLEAN NOT NULL,
    bonus_set         INTEGER NOT NULL,
    left_hand_slot    BIGINT REFERENCES body_parts (body_part_id),
    right_hand_slot   BIGINT REFERENCES body_parts (body_part_id),
    left_leg_slot     BIGINT REFERENCES body_parts (body_part_id),
    right_leg_slot    BIGINT REFERENCES body_parts (body_part_id),
    upper_body_slot   BIGINT REFERENCES body_parts (body_part_id),
    head_slot         BIGINT REFERENCES body_parts (body_part_id)
);