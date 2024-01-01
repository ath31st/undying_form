CREATE TABLE scientist_negative_traits
(
    scientist_id      BIGSERIAL REFERENCES scientists (scientist_id),
    negative_trait_id SERIAL REFERENCES negative_traits (negative_trait_id),
    PRIMARY KEY (scientist_id, negative_trait_id)
);