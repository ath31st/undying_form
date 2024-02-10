CREATE TABLE player_actions
(
    player_action_id UUID PRIMARY KEY,
    scholar_id       BIGINT REFERENCES scholars (scholar_id) NOT NULL,
    action_type      INTEGER                                 NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiration_date  TIMESTAMP
);