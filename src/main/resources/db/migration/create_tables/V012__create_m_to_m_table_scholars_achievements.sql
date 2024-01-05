CREATE TABLE scholars_achievements
(
    scholar_id     BIGSERIAL REFERENCES scholars (scholar_id),
    achievement_id SERIAL REFERENCES achievements (achievement_id),
    PRIMARY KEY (scholar_id, achievement_id)
);
