--User entity
CREATE SEQUENCE "users_id_seq" INCREMENT BY 1 START WITH 1 CACHE 1;
CREATE TABLE "users"
(
    "id"         INT     NOT NULL,
    "uuid"       UUID    NOT NULL,
    "first_name" VARCHAR NOT NULL,
    "last_name"  VARCHAR NOT NULL,
    "username"   VARCHAR NOT NULL,
    "password"   VARCHAR NULL,
    "active"     BOOLEAN NOT NULL,
    "created_on" DATE    NOT NULL,
    "updated_on" DATE    NULL,
    CONSTRAINT "users_pk" PRIMARY KEY ("id"),
    CONSTRAINT "users_uuid_uk" UNIQUE ("uuid"),
    CONSTRAINT "users_username_uk" UNIQUE ("username")
);

--User Profile entity
CREATE SEQUENCE "user_profiles_id_seq" INCREMENT BY 1 START WITH 1 CACHE 1;
CREATE TABLE "user_profiles"
(
    "id"         INT  NOT NULL,
    "uuid"       UUID NOT NULL,
    "user_uuid"  UUID NOT NULL,
    "birth_date" DATE NOT NULL,
    CONSTRAINT "user_profiles_pk" PRIMARY KEY ("id"),
    CONSTRAINT "user_profiles_uuid_uk" UNIQUE ("uuid"),
    CONSTRAINT "user_profiles_user_uuid_uk" UNIQUE ("user_uuid"),
    CONSTRAINT "user_profiles__users_fk" FOREIGN KEY ("user_uuid") REFERENCES "users" ("uuid")
);
