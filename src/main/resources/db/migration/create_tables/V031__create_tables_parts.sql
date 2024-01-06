CREATE TABLE hands
(
    hand_id       BIGSERIAL PRIMARY KEY,
    quality       INTEGER     NOT NULL CHECK (quality >= 0),
    integrity     INTEGER     NOT NULL CHECK (integrity >= 0),
    side          VARCHAR(10) NOT NULL CHECK (side IN ('left', 'right')),
    type_hands_id INTEGER REFERENCES type_hands (type_hands_id)
);

CREATE TABLE legs
(
    leg_id       BIGSERIAL PRIMARY KEY,
    quality      INTEGER     NOT NULL CHECK (quality >= 0),
    integrity    INTEGER     NOT NULL CHECK (integrity >= 0),
    side         VARCHAR(10) NOT NULL CHECK (side IN ('left', 'right')),
    type_legs_id INTEGER REFERENCES type_legs (type_legs_id)
);

CREATE TABLE upper_bodies
(
    upper_body_id        BIGSERIAL PRIMARY KEY,
    quality              INTEGER NOT NULL CHECK (quality >= 0),
    integrity            INTEGER NOT NULL CHECK (integrity >= 0),
    type_upper_bodies_id INTEGER REFERENCES type_upper_bodies (type_upper_bodies_id)
);

CREATE TABLE heads
(
    head_id       BIGSERIAL PRIMARY KEY,
    quality       INTEGER NOT NULL CHECK (quality >= 0),
    integrity     INTEGER NOT NULL CHECK (integrity >= 0),
    type_heads_id INTEGER REFERENCES type_heads (type_heads_id)
);
