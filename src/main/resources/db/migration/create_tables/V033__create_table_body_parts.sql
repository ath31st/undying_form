CREATE TABLE body_parts
(
    body_part_id          BIGSERIAL PRIMARY KEY,
    quality               INTEGER NOT NULL CHECK (quality >= 0),
    integrity             INTEGER NOT NULL CHECK (integrity >= 0),
    body_part_template_id INTEGER REFERENCES body_part_templates (body_part_template_id)
);