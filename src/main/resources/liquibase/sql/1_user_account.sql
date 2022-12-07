--liquibase formatted sql

--changeset samael:1

CREATE TABLE users_account
(
    id         SERIAL,
    first_name text,
    last_name  text,
    email      text,
    password   text,
    phone      text,
    role       text,
    avatar_id  BIGINT,
    CONSTRAINT pk_users_account PRIMARY KEY (id),
    CONSTRAINT un_email unique(email)
);