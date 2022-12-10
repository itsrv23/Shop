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

INSERT INTO public.users_account ("email", "password", "role")
VALUES
    (E'user@gmail.com', E'$2a$10$z4L2CvJCdVe2Lfjc1tS5W.WAJ6LECIExfcj.LRhfZy8q.XmaVgorG', E'USER'),
    (E'admin@gmail.com', E'$2a$10$myymLHEXGSe9LigC8WgleOiEbn2LToNzeP17rzm788PMfz9siMGjS', E'ADMIN');

INSERT INTO public.ads ("user_id", "description", "image", "price", "title")
VALUES
    (1, E'2132342', E'image', 150, E'title'),
    (1, E'3432432423', E'image', 100, E'title');