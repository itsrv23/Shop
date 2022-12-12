--liquibase formatted sql

--changeset samael:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users_account
(
    id         SERIAL,
    first_name text,
    last_name  text,
    email      text,
    password   text,
    phone      text,
    role       text,
    avatar_id  UUID,
    CONSTRAINT pk_users_account PRIMARY KEY (id),
    CONSTRAINT un_email unique(email)
);