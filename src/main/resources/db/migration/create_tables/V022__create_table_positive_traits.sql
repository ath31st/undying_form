CREATE TABLE positive_traits
(
    positive_trait_id     SERIAL PRIMARY KEY,
    name                  VARCHAR(255)                                     NOT NULL,
    description           TEXT                                             NOT NULL,
    alchemy_bonus         INT,
    biology_bonus         INT,
    engineering_bonus     INT,
    physical_health_bonus INT,
    mental_health_bonus   INT,
    weight                INT,
    trait_group_id        INTEGER REFERENCES trait_groups (trait_group_id) NOT NULL
);