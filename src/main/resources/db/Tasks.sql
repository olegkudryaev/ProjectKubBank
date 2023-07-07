-- liquibase formatted sql
-- changeset liquibase:1
CREATE TABLE IF NOT EXISTS public."Tasks"
(
    "id" uuid NOT NULL,
    "title" varchar(255) NOT NULL,
    "description" varchar(255) NOT NULL,
    "time" TIMESTAMP NOT NULL,
    "status" varchar(255) NOT NULL,
    "performer" uuid,
    CONSTRAINT "PK_Tasks" PRIMARY KEY ("id"),
    CONSTRAINT "FK_performer" FOREIGN KEY ("performer")
        REFERENCES public."Workers" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- changeset liquibase:2
    INSERT INTO public."Tasks" ("id", "title", "description", "time", "status", "performer")
    VALUES ('6f7907f0-2609-11ec-9621-0242ac130002', 'Task title1', 'Task description1', '2023-07-22 12:00:00', 'In progress', 'ea89ae04-1c81-11ee-be56-0242ac120002');
    INSERT INTO public."Tasks" ("id", "title", "description", "time", "status", "performer")
    VALUES ('cebf023c-83ef-49f4-a430-97d6de0cef7e', 'Task title2', 'Task description2', '2023-07-25 12:00:00', 'In progress', 'f4d3e8fc-1c81-11ee-be56-0242ac120002');
    INSERT INTO public."Tasks" ("id", "title", "description", "time", "status", "performer")
    VALUES ('e6ef0910-1c81-11ee-be56-0242ac120002', 'Task title3', 'Task description3', '2023-07-23 12:00:00', 'In progress', 'f925e2e8-1c81-11ee-be56-0242ac120002');
    INSERT INTO public."Tasks" ("id", "title", "description", "time", "status", "performer")
    VALUES ('4774719e-1c82-11ee-be56-0242ac120002', 'Task title4', 'Task description4', '2023-07-28 12:00:00', 'In progress', 'f925e2e8-1c81-11ee-be56-0242ac120002');
