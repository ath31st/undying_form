CREATE TABLE items_storages
(
    item_id    SERIAL REFERENCES items (item_id),
    storage_id BIGINT REFERENCES storages (storage_id),
    quantity   INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    PRIMARY KEY (item_id, storage_id)
);
