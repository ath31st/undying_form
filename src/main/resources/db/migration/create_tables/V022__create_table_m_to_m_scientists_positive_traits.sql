CREATE TABLE scientist_positive_traits
(
    scientist_id      BIGSERIAL REFERENCES scientists (scientist_id),
    positive_trait_id SERIAL REFERENCES positive_traits (positive_trait_id),
    PRIMARY KEY (scientist_id, positive_trait_id)
);