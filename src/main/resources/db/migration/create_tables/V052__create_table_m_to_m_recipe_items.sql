CREATE TABLE recipe_items
(
    recipe_id SERIAL REFERENCES recipes (recipe_id),
    item_id   SERIAL REFERENCES items (item_id),
    quantity  INTEGER DEFAULT 1 CHECK (quantity >= 1),
    PRIMARY KEY (recipe_id, item_id)
);