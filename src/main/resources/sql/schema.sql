CREATE TABLE IF NOT EXISTS "user"
(
    id         				UUID NOT NULL,
    username     			VARCHAR(100) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS subscription
(
    id         				UUID NOT NULL,
    name     			    VARCHAR(100) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_subscription
(
    user_id         		UUID NOT NULL,
    sub_id     			    UUID NOT NULL,
    PRIMARY KEY (user_id, sub_id)
);