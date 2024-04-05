CREATE OR REPLACE FUNCTION limit_recipe_items()
    RETURNS TRIGGER AS
$$
DECLARE
    recipe_count INT;
BEGIN
    SELECT COUNT(*)
    INTO recipe_count
    FROM recipe_items
    WHERE recipe_id = NEW.recipe_id;

    IF recipe_count >= 4 THEN
        RAISE EXCEPTION 'Cannot insert more than 4 items in a recipe';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER limit_recipe_items_trigger
    BEFORE INSERT
    ON recipe_items
    FOR EACH ROW
EXECUTE FUNCTION limit_recipe_items();

