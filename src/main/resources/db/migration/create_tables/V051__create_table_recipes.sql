CREATE TABLE recipes
(
    recipe_id   SERIAL PRIMARY KEY,
    rarity      INT          NOT NULL DEFAULT 1,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);