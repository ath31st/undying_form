CREATE TABLE trait_groups
(
    trait_group_id serial PRIMARY KEY,
    name           varchar(255) UNIQUE NOT NULL,
    description    TEXT                NOT NULL
);
