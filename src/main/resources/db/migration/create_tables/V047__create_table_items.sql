CREATE TABLE items
(
    item_id     SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    rarity      INTEGER      NOT NULL DEFAULT 1
);
