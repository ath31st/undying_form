CREATE TABLE recipe_book_recipes
(
    recipe_book_id BIGSERIAL REFERENCES recipe_books (recipe_book_id),
    recipe_id      SERIAL REFERENCES recipes (recipe_id),
    PRIMARY KEY (recipe_book_id, recipe_id)
);