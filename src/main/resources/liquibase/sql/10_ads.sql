--liquibase formatted sql

--changeset samael:10
CREATE TABLE ads
(
    id          serial,
    user_id     int,
    description text,
    image       text,
    price       INTEGER,
    title       text,
    CONSTRAINT pk_ads PRIMARY KEY (id)
);

create index ads_user_id_index on ads using btree (user_id);

ALTER TABLE ads
    ADD CONSTRAINT ads_user_id_fk FOREIGN KEY (user_id)
        REFERENCES users_account(id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

INSERT INTO public.users_account ("first_name", "last_name", "email", "password", "phone", "role", "avatar_id")
VALUES
    (NULL, NULL, E'test@mail.ru', E'a5f726cd35b69e3b46ec92868cf944d2', NULL, E'USER', NULL);

INSERT INTO public.ads ("user_id", "description", "image", "price", "title")
VALUES
    (1, E'2132342', E'image', 150, E'title'),
    (1, E'3432432423', E'image', 100, E'title');