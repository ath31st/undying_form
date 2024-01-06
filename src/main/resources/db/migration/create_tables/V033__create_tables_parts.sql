CREATE TABLE hands
(
    hand_id      BIGSERIAL PRIMARY KEY,
    quality      INTEGER     NOT NULL CHECK (quality >= 0),
    integrity    INTEGER     NOT NULL CHECK (integrity >= 0),
    side         VARCHAR(10) NOT NULL CHECK (side IN ('left', 'right')),
    hand_type_id INTEGER REFERENCES hand_types (hand_type_id)
);

CREATE TABLE legs
(
    leg_id      BIGSERIAL PRIMARY KEY,
    quality     INTEGER     NOT NULL CHECK (quality >= 0),
    integrity   INTEGER     NOT NULL CHECK (integrity >= 0),
    side        VARCHAR(10) NOT NULL CHECK (side IN ('left', 'right')),
    leg_type_id INTEGER REFERENCES leg_types (leg_type_id)
);

CREATE TABLE upper_bodies
(
    upper_body_id      BIGSERIAL PRIMARY KEY,
    quality            INTEGER NOT NULL CHECK (quality >= 0),
    integrity          INTEGER NOT NULL CHECK (integrity >= 0),
    upper_body_type_id INTEGER REFERENCES upper_body_types (upper_body_type_id)
);

CREATE TABLE heads
(
    head_id      BIGSERIAL PRIMARY KEY,
    quality      INTEGER NOT NULL CHECK (quality >= 0),
    integrity    INTEGER NOT NULL CHECK (integrity >= 0),
    head_type_id INTEGER REFERENCES head_types (head_type_id)
);
