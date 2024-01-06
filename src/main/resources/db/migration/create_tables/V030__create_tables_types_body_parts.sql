CREATE TABLE type_hands
(
    type_hands_id SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    strength      INTEGER      NOT NULL CHECK (strength >= 0),
    agility       INTEGER      NOT NULL CHECK (agility >= 0),
    endurance     INTEGER      NOT NULL CHECK (endurance >= 0)
);

CREATE TABLE type_legs
(
    type_legs_id SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    strength     INTEGER      NOT NULL CHECK (strength >= 0),
    agility      INTEGER      NOT NULL CHECK (agility >= 0),
    endurance    INTEGER      NOT NULL CHECK (endurance >= 0)
);

CREATE TABLE type_upper_bodies
(
    type_upper_bodies_id SERIAL PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    description          TEXT,
    strength             INTEGER      NOT NULL CHECK (strength >= 0),
    agility              INTEGER      NOT NULL CHECK (agility >= 0),
    endurance            INTEGER      NOT NULL CHECK (endurance >= 0)
);

CREATE TABLE type_heads
(
    type_heads_id SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    strength      INTEGER      NOT NULL CHECK (strength >= 0),
    agility       INTEGER      NOT NULL CHECK (agility >= 0),
    endurance     INTEGER      NOT NULL CHECK (endurance >= 0)
);