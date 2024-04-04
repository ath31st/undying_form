ALTER TABLE users
    ADD COLUMN recipe_book_id BIGINT UNIQUE NOT NULL REFERENCES recipe_books (recipe_book_id) default -1;