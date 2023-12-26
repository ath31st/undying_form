CREATE TABLE achievements
(
    achievement_id SERIAL PRIMARY KEY,
    name           VARCHAR(150) NOT NULL,
    description    TEXT         NOT NULL,
    date_obtained  TIMESTAMP    NOT NULL
);
