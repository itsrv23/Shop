--liquibase formatted sql

--changeset samael:10
CREATE TABLE ads
(
    id          serial,
    user_id     int,
    description text,
    price       INTEGER,
    title       text,
    picture     uuid,
    CONSTRAINT pk_ads PRIMARY KEY (id)
);

create index ads_user_id_index on ads using btree (user_id);

ALTER TABLE ads
    ADD CONSTRAINT ads_user_id_fk FOREIGN KEY (user_id)
        REFERENCES users_account (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;


INSERT INTO public.ads ("user_id", "description", "price", "title")
VALUES (1, E' almost new PC mouse', 150, E'Mouse'),
       (2, E'used phone Xiaomi', 10200, E'Phone');