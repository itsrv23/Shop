--liquibase formatted sql

--changeset firiv:20

CREATE TABLE ads_comment
(
    id          SERIAL,
    user_id     int,
    ads_id      int,
    created_at  timestamp with time zone,
    text        text,
    CONSTRAINT  pk_ads_comment PRIMARY KEY (id)
);

create index ads_comment_user_id_index on ads_comment using btree (user_id);
create index ads_comment_ads_id_index on ads_comment using btree (ads_id);

ALTER TABLE ads_comment
    ADD CONSTRAINT ads_comment_user_id_fk FOREIGN KEY (user_id)
        REFERENCES users_account(id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE ads_comment
    ADD CONSTRAINT ads_comment_ads_id_fk FOREIGN KEY (ads_id)
        REFERENCES ads(id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

INSERT INTO public.ads_comment ("user_id", "ads_id", "created_at", "text")
VALUES
    (1, 1, E'2000-01-23T04:56:07.000+00:00', E'text of comment1'),
    (1, 1, E'2000-01-23T08:20:25.000+00:00', E'text of comment2');