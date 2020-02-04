CREATE TABLE if not exists USERS
(
    id          serial PRIMARY KEY,
    username    VARCHAR(20) UNIQUE,
    password    CHAR(255),
    salt        CHAR(16),
    role        INTEGER
);

CREATE TABLE if not exists MASTER_ADMIN
(
    id                  serial PRIMARY KEY,
    master_username     CHAR(8),
    master_password     CHAR(8)
);

-- Creates / Destroys initial login details
DO
$init_login_details$
BEGIN

IF EXISTS (SELECT FROM USERS) THEN
	TRUNCATE MASTER_ADMIN RESTART IDENTITY;
ELSE
	TRUNCATE MASTER_ADMIN RESTART IDENTITY;
	INSERT INTO MASTER_ADMIN (master_username, master_password)
	VALUES ('SKELETOR', 'GRAYSKUL');
END IF;

END
$init_login_details$