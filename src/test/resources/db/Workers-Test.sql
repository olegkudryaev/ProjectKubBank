-- liquibase formatted sql
-- changeset liquibase:1
CREATE TABLE IF NOT EXISTS public."Workers"
(
    "id" uuid NOT NULL,
    "name" varchar(255) NOT NULL,
    "position" varchar(255) NOT NULL,
    "avatar" text NOT NULL,
    CONSTRAINT "PK_Workers" PRIMARY KEY ("id")
);

    INSERT INTO public."Workers" ("id", "name", "position", "avatar")
    VALUES ('ea89ae04-1c81-11ee-be56-0242ac120002', 'John Doe', 'Developer', 'http://example.com/avatar.png');
    INSERT INTO public."Workers" ("id", "name", "position", "avatar")
    VALUES ('f4d3e8fc-1c81-11ee-be56-0242ac120002', 'John Smith', 'Manager', 'http://example.com/avatar.png');
    INSERT INTO public."Workers" ("id", "name", "position", "avatar")
    VALUES ('f925e2e8-1c81-11ee-be56-0242ac120002', 'John Kent', 'Boss', 'http://example.com/avatar.png');