CREATE TABLE trait_groups
(
    trait_group_id SERIAL PRIMARY KEY,
    name           varchar(255) UNIQUE NOT NULL,
    description    TEXT                NOT NULL
);
