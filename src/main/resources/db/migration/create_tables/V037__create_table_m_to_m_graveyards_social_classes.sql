CREATE TABLE graveyard_social_classes
(
    graveyard_id    SERIAL REFERENCES graveyards (graveyard_id),
    social_class_id SERIAL REFERENCES social_classes (social_class_id),
    PRIMARY KEY (graveyard_id, social_class_id)
);