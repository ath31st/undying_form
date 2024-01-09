CREATE TABLE graveyard_social_classes
(
    graveyard_id    INT REFERENCES graveyards (graveyard_id),
    social_class_id INT REFERENCES social_classes (social_class_id),
    PRIMARY KEY (graveyard_id, social_class_id)
);