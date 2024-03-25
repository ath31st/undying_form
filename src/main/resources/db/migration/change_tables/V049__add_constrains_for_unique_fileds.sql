ALTER TABLE achievements
    ADD CONSTRAINT unique_achievement_name UNIQUE (name);

ALTER TABLE cities
    ADD CONSTRAINT unique_city_name UNIQUE (name);

ALTER TABLE districts
    ADD CONSTRAINT unique_district_name UNIQUE (name);

ALTER TABLE graveyards
    ADD CONSTRAINT unique_graveyard_name UNIQUE (name);

ALTER TABLE education
    ADD CONSTRAINT unique_faculty_name UNIQUE (faculty);

ALTER TABLE specializations
    ADD CONSTRAINT unique_specialization_name UNIQUE (name);

ALTER TABLE positive_traits
    ADD CONSTRAINT unique_positive_trait_name UNIQUE (name);

ALTER TABLE negative_traits
    ADD CONSTRAINT unique_negative_trait_name UNIQUE (name);

ALTER TABLE body_part_templates
    ADD CONSTRAINT unique_body_part_template_name UNIQUE (name);

ALTER TABLE items
    ADD CONSTRAINT unique_item_name UNIQUE (name);