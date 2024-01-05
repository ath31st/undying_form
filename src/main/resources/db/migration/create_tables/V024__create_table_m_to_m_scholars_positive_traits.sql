CREATE TABLE scholar_positive_traits
(
    scholar_id      BIGSERIAL REFERENCES scholars (scholar_id),
    positive_trait_id SERIAL REFERENCES positive_traits (positive_trait_id),
    PRIMARY KEY (scholar_id, positive_trait_id)
);