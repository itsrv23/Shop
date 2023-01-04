--liquibase formatted sql

--changeSet Sergey:40


CREATE TABLE ads_pictures
(
    uuid         uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content_size BIGINT      NOT NULL,
    media_type   VARCHAR(50) NOT NULL,
    file_bytes   oid,
    file_name    text        NOT NULL,
    file_path    TEXT
);
