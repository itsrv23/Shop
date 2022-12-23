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
    CONSTRAINT un_email unique (email)
);

INSERT INTO public.users_account (first_name, last_name, "email", "password", "role")
VALUES ('Donald', 'Trump', E'user@gmail.com', E'$2a$10$z4L2CvJCdVe2Lfjc1tS5W.WAJ6LECIExfcj.LRhfZy8q.XmaVgorG',
        E'ROLE_USER'),
       ('Rick', 'Thompson', E'admin@gmail.com', E'$2a$10$myymLHEXGSe9LigC8WgleOiEbn2LToNzeP17rzm788PMfz9siMGjS',
        E'ROLE_ADMIN');