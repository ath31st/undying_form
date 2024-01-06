CREATE TABLE hand_types
(
    hand_type_id    SERIAL PRIMARY KEY,
    name            VARCHAR(255)                                        NOT NULL,
    description     TEXT,
    strength        INTEGER                                             NOT NULL CHECK (strength >= 0),
    agility         INTEGER                                             NOT NULL CHECK (agility >= 0),
    endurance       INTEGER                                             NOT NULL CHECK (endurance >= 0),
    social_class_id INTEGER REFERENCES social_classes (social_class_id) NOT NULL
);

CREATE TABLE leg_types
(
    leg_type_id     SERIAL PRIMARY KEY,
    name            VARCHAR(255)                                        NOT NULL,
    description     TEXT,
    strength        INTEGER                                             NOT NULL CHECK (strength >= 0),
    agility         INTEGER                                             NOT NULL CHECK (agility >= 0),
    endurance       INTEGER                                             NOT NULL CHECK (endurance >= 0),
    social_class_id INTEGER REFERENCES social_classes (social_class_id) NOT NULL
);

CREATE TABLE upper_body_types
(
    upper_body_type_id SERIAL PRIMARY KEY,
    name               VARCHAR(255)                                        NOT NULL,
    description        TEXT,
    strength           INTEGER                                             NOT NULL CHECK (strength >= 0),
    agility            INTEGER                                             NOT NULL CHECK (agility >= 0),
    endurance          INTEGER                                             NOT NULL CHECK (endurance >= 0),
    social_class_id    INTEGER REFERENCES social_classes (social_class_id) NOT NULL
);

CREATE TABLE head_types
(
    head_type_id    SERIAL PRIMARY KEY,
    name            VARCHAR(255)                                        NOT NULL,
    description     TEXT,
    strength        INTEGER                                             NOT NULL CHECK (strength >= 0),
    agility         INTEGER                                             NOT NULL CHECK (agility >= 0),
    endurance       INTEGER                                             NOT NULL CHECK (endurance >= 0),
    social_class_id INTEGER REFERENCES social_classes (social_class_id) NOT NULL
);