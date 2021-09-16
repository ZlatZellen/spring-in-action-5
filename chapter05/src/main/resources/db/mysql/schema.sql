CREATE TABLE IF NOT EXISTS users (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)     NOT NULL,
    password   VARCHAR(255)    NOT NULL,
    full_name  VARCHAR(50)     NOT NULL,
    street     VARCHAR(50)     NOT NULL,
    city       VARCHAR(50)     NOT NULL,
    state      VARCHAR(2)      NOT NULL,
    zip_code   VARCHAR(10)     NOT NULL,
    phone      VARCHAR(11)     NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (username)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ingredients (
    id   VARCHAR(4)  NOT NULL PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS tacos (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)     NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS taco_ingredients (
    taco_id       BIGINT UNSIGNED NOT NULL,
    ingredient_id VARCHAR(4)      NOT NULL,
    FOREIGN KEY (taco_id) REFERENCES tacos (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS taco_orders (
    id                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(50)     NOT NULL,
    street               VARCHAR(50)     NOT NULL,
    city                 VARCHAR(50)     NOT NULL,
    state                VARCHAR(2)      NOT NULL,
    zip_code             VARCHAR(10)     NOT NULL,
    card_number          VARCHAR(16)     NOT NULL,
    card_expiration_date VARCHAR(5)      NOT NULL,
    cvv                  VARCHAR(3)      NOT NULL,
    user_id              BIGINT UNSIGNED NOT NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS taco_order_tacos (
    taco_order_id BIGINT UNSIGNED NOT NULL,
    taco_id       BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (taco_order_id) REFERENCES taco_orders (id),
    FOREIGN KEY (taco_id) REFERENCES tacos (id)
) ENGINE = InnoDB;
