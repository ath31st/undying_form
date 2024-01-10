CREATE TABLE recipe_books
(
    recipe_book_id            BIGSERIAL PRIMARY KEY,
    title                     VARCHAR(255) NOT NULL,
    previous_owner_first_name VARCHAR(255) NOT NULL,
    previous_owner_last_name  VARCHAR(255) NOT NULL
);
