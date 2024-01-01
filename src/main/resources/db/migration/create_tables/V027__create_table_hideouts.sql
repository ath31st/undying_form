CREATE TABLE hideouts
(
    hideout_id            BIGSERIAL PRIMARY KEY,
    district_id           INTEGER REFERENCES districts (district_id),
    name                  VARCHAR(255) NOT NULL,
    alchemy_equipment     INT,
    biology_equipment     INT,
    engineering_equipment INT
);