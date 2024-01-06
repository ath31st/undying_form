CREATE TABLE social_classes
(
    social_class_id SERIAL PRIMARY KEY,
    name            VARCHAR(255) UNIQUE NOT NULL,
    description     TEXT
);