CREATE TABLE users
(
    id            INTEGER PRIMARY KEY,
    username      VARCHAR(300) NOT NULL,
    name          VARCHAR(300) NOT NULL,
    register_date DATE,
    is_active     BOOLEAN,
    is_not_blocked    BOOLEAN
);