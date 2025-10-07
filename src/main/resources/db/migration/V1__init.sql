CREATE TABLE IF NOT EXISTS tw_user (
    id          uuid PRIMARY KEY,
    name        text NOT NULL,
    surname     text NOT NULL,
    email       text UNIQUE
);

CREATE TABLE IF NOT EXISTS tw_payment_method (
    id          uuid PRIMARY KEY,
    type        text NOT NULL,
    user_id     uuid NOT NULL,
    card_number  text NOT NULL,
    expiration_month int NOT NULL,
    expiration_year  int NOT NULL,
    cvv         int NOT NULL,

    CONSTRAINT fk_payment_method_user
      FOREIGN KEY (user_id) REFERENCES tw_user (id)
);

CREATE TABLE IF NOT EXISTS tw_subscription (
    id          uuid PRIMARY KEY,
    type        text NOT NULL,
    user_id     uuid NOT NULL,
    start_date  date NOT NULL,
    amount      decimal(10,2) NOT NULL,
    status      text NOT NULL,
    next_payment_date  date,

    CONSTRAINT fk_subscription_user
     FOREIGN KEY (user_id) REFERENCES tw_user (id)
);

-- index for subscription
CREATE INDEX IF NOT EXISTS idx_subscription_1
    ON tw_subscription (status, next_payment_date);

CREATE TABLE IF NOT EXISTS tw_payment (
    id          uuid PRIMARY KEY,
    subscription_id     uuid NOT NULL,
    payment_method_id   uuid,
    payment_month       int NOT NULL,
    payment_year        int NOT NULL,
    status      text NOT NULL,

    CONSTRAINT fk_payment_subscription
        FOREIGN KEY (subscription_id) REFERENCES tw_subscription (id),
    CONSTRAINT fk_payment_payment_method
        FOREIGN KEY (payment_method_id) REFERENCES tw_payment_method (id)
);

-- index for payment
CREATE UNIQUE INDEX IF NOT EXISTS idx_payment_1
    ON tw_payment (subscription_id, payment_year, payment_month);