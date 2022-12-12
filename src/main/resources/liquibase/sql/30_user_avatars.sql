--liquibase formatted sql

--changeset samael:30
CREATE TABLE public.user_avatars
(
    uuid  UUID DEFAULT uuid_generate_v4() NOT NULL,
    user_id integer unique,
    image TEXT,
    PRIMARY KEY (uuid)
)