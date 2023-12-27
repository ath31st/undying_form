CREATE TABLE scientists
(
    scientist_id           BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL,
    age                    INT,
    physical_health        INT,
    mental_health          INT,
    experience             INT,
    alchemy                INT,
    biology                INT,
    engineering            INT,
    successful_experiments INT
--     negative_traits_id     INT REFERENCES negative_traits (negative_traits_id),
);
