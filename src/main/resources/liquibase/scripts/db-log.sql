-- liquibase formatted sql

-- changeSet asorov:1
CREATE TABLE employee
(
    id                BIGSERIAL   NOT NULL PRIMARY KEY,
    first_name        VARCHAR(50) NOT NULL,
    last_name         VARCHAR(50) NOT NULL,
    phone             VARCHAR(50) NOT NULL,
    employee_position VARCHAR(50) NOT NULL
);

CREATE TABLE category
(
    id            BIGSERIAL   NOT NULL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

CREATE TABLE office
(
    id              BIGSERIAL    NOT NULL PRIMARY KEY,
    office_number   VARCHAR(100) NOT NULL,
    building_number VARCHAR(100) NOT NULL,
    description     TEXT
);

CREATE TABLE unit
(
    id               BIGSERIAL    NOT NULL PRIMARY KEY,
    model            VARCHAR(250) NOT NULL,
    serial_number    VARCHAR(250) NOT NULL,
    inventory_number VARCHAR(250) NOT NULL,
    income_date      DATE         NOT NULL,
    outcome_date     DATE,
    description      TEXT,
    employee_id      BIGINT,
    category_id      BIGINT       NOT NULL,
    office_id        BIGINT
);

CREATE TABLE users
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    employee_id BIGINT    NOT NULL
);

CREATE TABLE requests
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    request_date DATE      NOT NULL,
    description  TEXT,
    user_id      BIGINT    NOT NULL
);

CREATE TABLE software
(
    id            BIGSERIAL    NOT NULL PRIMARY KEY,
    install_date  DATE,
    software_name VARCHAR(250) NOT NULL
);

CREATE TABLE transaction
(
    id               BIGSERIAL    NOT NULL PRIMARY KEY,
    action_name      VARCHAR(100) NOT NULL,
    transaction_date DATE         NOT NULL,
    description      TEXT,
    asset            VARCHAR(100) NOT NULL,
    unit_id          BIGINT       NOT NULL,
    software_id      BIGINT       NOT NULL
);

-- changeSet asorov:2
ALTER TABLE unit
    ADD CONSTRAINT fk_unit_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id),
ADD CONSTRAINT fk_unit_category_id FOREIGN KEY (category_id) REFERENCES category (id),
ADD CONSTRAINT fk_unit_office_id FOREIGN KEY (office_id) REFERENCES office (id);

ALTER TABLE users
    ADD CONSTRAINT fk_users_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE requests
    ADD CONSTRAINT fk_requests_user_id FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE transaction
    ADD CONSTRAINT fk_transaction_unit_id FOREIGN KEY (unit_id) REFERENCES unit (id),
    ADD CONSTRAINT fk_transaction_software_id FOREIGN KEY (software_id) REFERENCES software (id);;

-- changeSet asorov:3
ALTER TABLE users
    ADD COLUMN username VARCHAR(100) NOT NULL;
ALTER TABLE users
    ADD COLUMN password VARCHAR(100) NOT NULL;
ALTER TABLE users
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

create index users_username_index
    on users (username);
alter table users
    add constraint users_pk
        unique (username);

create table authorities
(
    id        SERIAL PRIMARY KEY,
    username  varchar(30) not null ,
    authority varchar(30) not null
);

alter table authorities
    ADD CONSTRAINT authorities_username FOREIGN KEY (username) REFERENCES users (username);

create index authorities_username_index
    on authorities (username);
alter table authorities
    add constraint authorities_uk
        unique (username, authority);

