CREATE TABLE monsters
(
    monster_id    BIGSERIAL PRIMARY KEY,
    invisibility  INTEGER                                      NOT NULL CHECK (invisibility >= 0),
    strength      INTEGER                                      NOT NULL CHECK (strength >= 0),
    agility       INTEGER                                      NOT NULL CHECK (agility >= 0),
    endurance     INTEGER                                      NOT NULL CHECK (endurance >= 0),
    stability     INTEGER                                      NOT NULL CHECK (stability >= 0),
    scholar_id    BIGINT REFERENCES scholars (scholar_id)      NOT NULL,
    body_parts_id BIGINT REFERENCES body_parts (body_parts_id) NOT NULL
);