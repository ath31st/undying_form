CREATE TABLE graveyards
(
    graveyard_id SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    capacity     INT,
    city_id      INT REFERENCES cities (city_id)
);

