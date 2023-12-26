CREATE TABLE education
(
    education_id SERIAL PRIMARY KEY,
    institution  VARCHAR(500) NOT NULL,
    alchemy      INT,
    biology      INT,
    engineering  INT,
    faculty      VARCHAR(255) NOT NULL
);