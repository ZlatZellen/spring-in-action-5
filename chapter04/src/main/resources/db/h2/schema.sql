DROP TABLE IF EXISTS taco_order_tacos;
DROP TABLE IF EXISTS taco_orders;
DROP TABLE IF EXISTS taco_ingredients;
DROP TABLE IF EXISTS tacos;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id         BIGINT IDENTITY PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(50)  NOT NULL,
    street     VARCHAR(50)  NOT NULL,
    city       VARCHAR(50)  NOT NULL,
    state      VARCHAR(2)   NOT NULL,
    zip_code   VARCHAR(10)  NOT NULL,
    phone      VARCHAR(11)  NOT NULL,
    created_at TIMESTAMP    NOT NULL
);

CREATE TABLE ingredients (
    id   VARCHAR(4) PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE tacos (
    id         BIGINT IDENTITY PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL
);

CREATE TABLE taco_ingredients (
    taco_id       BIGINT     NOT NULL,
    ingredient_id VARCHAR(4) NOT NULL
);
ALTER TABLE taco_ingredients
    ADD FOREIGN KEY (taco_id) REFERENCES tacos (id);
ALTER TABLE taco_ingredients
    ADD FOREIGN KEY (ingredient_id) REFERENCES ingredients (id);

CREATE TABLE taco_orders (
    id                   BIGINT IDENTITY PRIMARY KEY,
    name                 VARCHAR(50) NOT NULL,
    street               VARCHAR(50) NOT NULL,
    city                 VARCHAR(50) NOT NULL,
    state                VARCHAR(2)  NOT NULL,
    zip_code             VARCHAR(10) NOT NULL,
    card_number          VARCHAR(16) NOT NULL,
    card_expiration_date VARCHAR(5)  NOT NULL,
    cvv                  VARCHAR(3)  NOT NULL,
    user_id              BIGINT      NOT NULL,
    created_at           TIMESTAMP   NOT NULL
);
ALTER TABLE taco_orders
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE taco_order_tacos (
    taco_order_id BIGINT NOT NULL,
    taco_id       BIGINT NOT NULL
);
ALTER TABLE taco_order_tacos
    ADD FOREIGN KEY (taco_order_id) REFERENCES taco_orders (id);
ALTER TABLE taco_order_tacos
    ADD FOREIGN KEY (taco_id) REFERENCES tacos (id);
