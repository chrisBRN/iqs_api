CREATE TABLE if not exists USERS
(
    id          serial PRIMARY KEY,
    username    VARCHAR(60) UNIQUE,
    password    CHAR(60),
    role        VARCHAR(12),
    email       VARCHAR(320)
);

CREATE TABLE signer (
	signer_id bool PRIMARY KEY DEFAULT TRUE,
	signer text,
	CONSTRAINT signer_uni CHECK (signer_id)
);

INSERT INTO signer (signer)
VALUES ('temp');