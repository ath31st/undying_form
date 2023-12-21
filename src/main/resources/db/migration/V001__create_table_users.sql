CREATE TABLE users
(
    id             INTEGER PRIMARY KEY,
    username       VARCHAR(300) UNIQUE NOT NULL,
    name           VARCHAR(300) NOT NULL,
    register_date  VARCHAR(100) NOT NULL,
    is_active      BOOLEAN,
    is_not_blocked BOOLEAN,
    role           INTEGER
);