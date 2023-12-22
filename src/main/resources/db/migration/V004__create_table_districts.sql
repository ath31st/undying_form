CREATE TABLE districts
(
    district_id SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    city_id     INT REFERENCES cities (city_id)
);
