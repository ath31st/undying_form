CREATE TABLE negative_traits
(
    negative_trait_id       SERIAL PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    description             TEXT         NOT NULL,
    alchemy_penalty         INT,
    biology_penalty         INT,
    engineering_penalty     INT,
    physical_health_penalty INT,
    mental_health_penalty   INT,
    weight                  INT
);