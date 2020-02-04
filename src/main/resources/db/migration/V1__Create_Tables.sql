CREATE TABLE if not exists USERS
(
    id          serial PRIMARY KEY,
    username    VARCHAR(60) UNIQUE,
    password    CHAR(60),
    role        VARCHAR(12)
);