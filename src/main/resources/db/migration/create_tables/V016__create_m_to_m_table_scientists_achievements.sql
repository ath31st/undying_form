CREATE TABLE scientists_achievements
(
    scientist_id   INT REFERENCES scientists (scientist_id),
    achievement_id INT REFERENCES achievements (achievement_id),
    PRIMARY KEY (scientist_id, achievement_id)
);
