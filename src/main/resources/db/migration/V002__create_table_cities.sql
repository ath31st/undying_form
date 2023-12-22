CREATE TABLE cities
(
    city_id         SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    population      INT,
    description     TEXT,
    foundation_year INT,
    flag_url        VARCHAR(500)
);