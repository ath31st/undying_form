CREATE TABLE scholars
(
    scholar_id             BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL,
    age                    INT,
    physical_health        INT,
    mental_health          INT,
    experience             INT,
    alchemy                INT,
    biology                INT,
    engineering            INT,
    successful_experiments INT
);
