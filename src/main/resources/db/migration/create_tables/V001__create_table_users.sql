CREATE TABLE users
(
    user_id        BIGSERIAL PRIMARY KEY,
    username       VARCHAR(300) UNIQUE NOT NULL,
    email          VARCHAR(300),
    register_date  DATE                NOT NULL,
    is_active      BOOLEAN,
    is_not_blocked BOOLEAN,
    role           INTEGER
);