CREATE TABLE scholar_negative_traits
(
    scholar_id      BIGSERIAL REFERENCES scholars (scholar_id),
    negative_trait_id SERIAL REFERENCES negative_traits (negative_trait_id),
    PRIMARY KEY (scholar_id, negative_trait_id)
);