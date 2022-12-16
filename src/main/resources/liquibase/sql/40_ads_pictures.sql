--liquibase formatted sql

--changeSet Sergey:40
ALTER TABLE ads
    ADD COLUMN image text UNIQUE;

CREATE TABLE ads_pictures
(
    uuid         uuid DEFAULT uuid_generate_v4() NOT NULL,
    content_size BIGINT                          NOT NULL,
    file_name    VARCHAR(255),
    media_type   VARCHAR(50)                     NOT NULL,
    file_bytes   oid,
    ads_id       INTEGER
);
