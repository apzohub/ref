--liquibase formatted sql

--changeset userx:1
create table entity (
    id varchar(36) primary key,
    version int,
    name varchar(60),
    description varchar(255),
    kv jsonb
);

--changeset userx:2
CREATE INDEX idxginp ON entity USING GIN (kv);

