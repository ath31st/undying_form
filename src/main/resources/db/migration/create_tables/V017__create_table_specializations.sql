CREATE TABLE specializations
(
    specialization_id SERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    description       TEXT         NOT NULL,
    alchemy_bonus     INT,
    biology_bonus     INT,
    engineering_bonus INT
);