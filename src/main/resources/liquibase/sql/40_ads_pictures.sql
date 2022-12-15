--liquibase formatted sql

--changeSet Sergey:40
ALTER TABLE ads
    ADD COLUMN image text UNIQUE;

CREATE TABLE ads_pictures
(
    id           INTEGER PRIMARY KEY,
    content_size INTEGER     NOT NULL,
    file_name    VARCHAR(255) REFERENCES ads (image),
    media_type   VARCHAR(50) NOT NULL,
    file_bytes   oid
);
