CREATE TABLE scientists_achievements
(
    scientist_id   BIGSERIAL REFERENCES scientists (scientist_id),
    achievement_id SERIAL REFERENCES achievements (achievement_id),
    PRIMARY KEY (scientist_id, achievement_id)
);
