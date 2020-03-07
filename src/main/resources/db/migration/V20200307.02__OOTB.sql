-- Users
INSERT INTO "users"
values (nextval('users_id_seq'), '1c7922e6-bf04-4e3a-a081-a30595913ac9', 'Ciprian', 'Stoian', 'ciprian.ioan.stoian@gmail.com',
        '$2a$10$FOqvwlrSrXq2yGkgBHHX1eSYSuK5lrKkaNntocjMmEXpmO0RcF3.y', true, '2020-03-07');

-- User Profiles
INSERT INTO "user_profiles"
values (nextval('user_profiles_id_seq'), '5e644531-0f47-4a5b-b1c5-954d6c3f44e5',
        (SELECT "uuid" FROM "users" WHERE "username" = 'ciprian.ioan.stoian@gmail.com'), '2020-03-07');