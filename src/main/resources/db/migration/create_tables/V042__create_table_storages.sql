CREATE TABLE storages
(
    storage_id BIGSERIAL PRIMARY KEY,
    capacity   INTEGER NOT NULL CHECK (capacity >= 0)
);
