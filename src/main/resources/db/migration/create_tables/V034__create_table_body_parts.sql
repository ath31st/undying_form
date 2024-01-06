CREATE TABLE body_parts
(
    body_parts_id BIGSERIAL PRIMARY KEY,
    left_hand_id  BIGINT REFERENCES hands (hand_id),
    right_hand_id BIGINT REFERENCES hands (hand_id),
    left_leg_id   BIGINT REFERENCES legs (leg_id),
    right_leg_id  BIGINT REFERENCES legs (leg_id),
    upper_body_id BIGINT REFERENCES upper_bodies (upper_body_id),
    head_id       BIGINT REFERENCES heads (head_id)
);