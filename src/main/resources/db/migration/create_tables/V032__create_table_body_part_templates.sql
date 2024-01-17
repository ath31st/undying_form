CREATE TABLE body_part_templates
(
    body_part_template_id SERIAL PRIMARY KEY,
    name                  VARCHAR(255)                                        NOT NULL,
    description           TEXT,
    is_active             BOOLEAN                                             NOT NULL,
    strength              INTEGER                                             NOT NULL CHECK (strength >= 0),
    agility               INTEGER                                             NOT NULL CHECK (agility >= 0),
    endurance             INTEGER                                             NOT NULL CHECK (endurance >= 0),
    body_part_group       INTEGER                                             NOT NULL,
    social_class_id       INTEGER REFERENCES social_classes (social_class_id) NOT NULL
);