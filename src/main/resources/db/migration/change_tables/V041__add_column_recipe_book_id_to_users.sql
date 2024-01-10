ALTER TABLE users
    ADD COLUMN recipe_book_id BIGINT UNIQUE REFERENCES recipe_books (recipe_book_id);