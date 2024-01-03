INSERT INTO negative_traits (name, description, is_active, alchemy_penalty, biology_penalty,
                             engineering_penalty, physical_health_penalty, mental_health_penalty,
                             weight, trait_group_id)
VALUES ('Неуклюжий Инженер',
        'Неуклюжий инженер часто сталкивается с трудностями при манипуляциях с техникой, что может привести к негативным последствиям.',
        true, 0, 0, -3, 0, 0, 3, 1),
       ('Скверный Механик',
        'Механик с сомнительными навыками, способен на частые ошибки и недоразумения в ходе работ над протезами и механизмами.',
        true, 0, 0, -2, 0, 0, 2, 1),
       ('Техник Растяпа',
        'Небрежность при работе с механизмами и техникой, может привести к несчастным случаям и негативным последствиям.',
        true, 0, 0, -1, 0, 0, 1, 1),
       ('Неопытный Биолог',
        'Плохое понимание внутренней анатомии и устройства живых организмов приводит к ужасным последствиям.',
        true, 0, -3, 0, 0, 0, 3, 2),
       ('Анатом Невежда',
        'Ограниченное понимание человеческой и животной анатомии оставляет ученого в неведении при работе над созданиями.',
        true, 0, -2, 0, 0, 0, 2, 2),
       ('Отчисленный Лаборант-Биолог',
        'Лаборант-биолог, который был отчислен из учебного заведения из-за недостаточных знаний и навыков.',
        true, 0, -1, 0, 0, 0, 1, 2),
       ('Некомпетентный Алхимик',
        'Неудачные попытки создания эликсиров делают ученого неловким в мире алхимии. Его формулы приносят, в основном, негативные результаты.',
        true, -3, 0, 0, 0, 0, 3, 3),
       ('Невежественный Зельевар',
        'Повышенная вероятность неудачного приготовления зелий и эликсиров. Их эффективность так же под остается сомнительной.',
        true, -2, 0, 0, 0, 0, 2, 3),
       ('Аптекарь Профан',
        'Отсутствие опыта в аптекарской сфере делает этого ученого неудачным в создании лекарственных препаратов и их воздействия на организмы.',
        true, -1, 0, 0, 0, 0, 1, 3),
       ('Дистрофия',
        'Низкий рост и слабое телосложение делают персонажа особенно уязвимым к физическим воздействиям.',
        true, 0, 0, 0, -30, 0, 3, 4),
       ('Гемофилия',
        'Болезнь крови стала для этого ученого поистине тяжкой учебой. Это влияет на его общую живучесть.',
        true, 0, 0, 0, -20, 0, 2, 4),
       ('Тонкая кожа',
        'Любой порез или царапина становятся большой проблемой, не говоря уже о серьезных травмах.',
        true, 0, 0, 0, -10, 0, 1, 4),
       ('Ментальная Слабость',
        'Непостоянство ума в стрессовых ситуациях. Ученый теряет ясность ума и фокус даже в обыденных ситуациях.',
        true, 0, 0, 0, 0, -30, 3, 5),
       ('Слабая Воля',
        'Неустойчивость перед искушениями или воздействиями делает волю ученого хрупкой, как стекло.',
        true, 0, 0, 0, 0, -20, 2, 5),
       ('Склонность к панике',
        'Внутренний баланс и спокойствие ученого часто подвергаются угрозе.',
        true, 0, 0, 0, 0, -10, 1, 5);
