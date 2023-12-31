INSERT INTO hand_templates (name, description, strength, agility, endurance, social_class_id)
VALUES ('Рука рабочего', 'Прочная рука для выполнения физических работ.', 2, 2, 2, 4),
       ('Рука бандита', 'Гибкая и ловкая рука для проведения быстрых действий.', 2, 3, 1, 3),
       ('Рука аристократа', 'Элегантная и изысканная рука.', 1, 3, 2, 1),
       ('Рука крестьянина', 'Простая и прочная рука для повседневных задач.', 3, 1, 2, 2);

INSERT INTO leg_templates (name, description, strength, agility, endurance, social_class_id)
VALUES ('Нога рабочего', 'Крепкая нога для выносливой ходьбы и стояния.', 2, 2, 2, 4),
       ('Нога бандита', 'Легкая и быстрая нога для проведения оперативных действий.', 2, 3, 1,
        3),
       ('Нога аристократа', 'Элегантная и изысканная нога.', 1, 3, 2, 1),
       ('Нога крестьянина', 'Прочная и устойчивая нога для тяжелых работ.', 3, 1, 2, 2);

INSERT INTO upper_body_templates (name, description, strength, agility, endurance, social_class_id)
VALUES ('Торс рабочего', 'Крепкий торс для выполнения физических работ.', 2, 2, 2, 4),
       ('Торс бандита', 'Легкий и подвижный торс для быстрых маневров.', 2, 3, 1, 3),
       ('Торс аристократа', 'Элегантный и изысканный торс.', 1, 3, 2, 1),
       ('Торс крестьянина', 'Прочный торс для тяжелых физических нагрузок.', 3, 1, 2, 2);

INSERT INTO head_templates (name, description, strength, agility, endurance, social_class_id)
VALUES ('Голова рабочего', 'Прочная голова для защиты от травм.', 2, 2, 2, 4),
       ('Голова бандита', 'Хитрая и острая голова для быстрых решений.', 2, 3, 1, 3),
       ('Голова аристократа', 'Элегантная и утонченная голова.', 1, 3, 2, 1),
       ('Голова крестьянина', 'Простая и устойчивая голова для повседневных задач.', 3, 1, 2, 2);
