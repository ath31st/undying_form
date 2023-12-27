CREATE TABLE hideouts
(
    hideout_id            BIGSERIAL PRIMARY KEY,
    scientist_id          BIGSERIAL REFERENCES scientists (scientist_id),
    district_id           SERIAL REFERENCES districts (district_id),
    name                  VARCHAR(255) NOT NULL,
    alchemy_equipment     INT,
    biology_equipment     INT,
    engineering_equipment INT
);