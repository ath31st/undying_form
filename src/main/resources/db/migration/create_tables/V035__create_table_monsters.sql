CREATE TABLE monsters
(
    monster_id        BIGSERIAL PRIMARY KEY,
    invisibility      INTEGER                                                      NOT NULL CHECK (invisibility >= 0),
    strength          INTEGER                                                      NOT NULL CHECK (strength >= 0),
    agility           INTEGER                                                      NOT NULL CHECK (agility >= 0),
    endurance         INTEGER                                                      NOT NULL CHECK (endurance >= 0),
    stability         INTEGER                                                      NOT NULL CHECK (stability >= 0),
    set_body_parts_id BIGINT UNIQUE REFERENCES sets_body_parts (set_body_parts_id) NOT NULL
);