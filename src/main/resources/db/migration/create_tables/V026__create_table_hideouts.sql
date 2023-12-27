CREATE TABLE hideouts
(
    hideout_id            BIGSERIAL PRIMARY KEY,
    district_id           SERIAL REFERENCES districts (district_id),
    name                  VARCHAR(255) NOT NULL,
    alchemy_equipment     INT,
    biology_equipment     INT,
    engineering_equipment INT
);