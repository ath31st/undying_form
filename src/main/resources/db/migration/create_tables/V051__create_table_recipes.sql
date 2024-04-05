CREATE TABLE recipes
(
    recipe_id   SERIAL PRIMARY KEY,
    rarity      INTEGER      NOT NULL DEFAULT 1,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);