CREATE TABLE caretakers
(
    caretaker_id          SERIAL PRIMARY KEY,
    name                  VARCHAR(255) NOT NULL,
    age                   INT,
    weapon_type           VARCHAR(50),
    is_alive              BOOLEAN,
    successful_defenses   INT,
    unsuccessful_defenses INT,
    graveyard_id          INT UNIQUE REFERENCES graveyards (graveyard_id)
);
