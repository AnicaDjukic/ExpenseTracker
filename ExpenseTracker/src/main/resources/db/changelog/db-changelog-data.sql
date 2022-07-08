--liquibase formatted sql

--changeset Anica:1

INSERT INTO role (ID, NAME)
VALUES (1, 'ROLE_USER');

INSERT INTO role (ID, NAME)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO users (ID, PASSWORD, USERNAME, ROLE_ID)
VALUES ('a8e2f208-b667-44e7-ae3a-9d0e044658b2', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'peraperic', 1);

INSERT INTO users (ID, PASSWORD, USERNAME, ROLE_ID)
VALUES ('a8e2f208-b667-44e7-ae3a-9d0e044658b3', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'mikamikic', 1);

INSERT INTO expense_group (ID, DESCRIPTION, NAME, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46321f', 'Pera expense group description', 'Pera Expense group', 'a8e2f208-b667-44e7-ae3a-9d0e044658b2');

INSERT INTO expense_group (ID, DESCRIPTION, NAME, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46322f', 'Pera new expense group description', 'Pera new expense group', 'a8e2f208-b667-44e7-ae3a-9d0e044658b2');

INSERT INTO expense_group (ID, DESCRIPTION, NAME, USER_ID)
VALUES ('57f8f3fa-e285-4895-8b9f-29391c46432e', 'Mika expense group description', 'Mika expense group', 'a8e2f208-b667-44e7-ae3a-9d0e044658b3');

INSERT INTO expense_group (ID, DESCRIPTION, NAME, USER_ID)
VALUES ('57f8f3fa-e285-4895-8b9f-29391c46433e', 'Mika new expense group description', 'Mika new expense group', 'a8e2f208-b667-44e7-ae3a-9d0e044658b3');

INSERT INTO expense (ID, AMOUNT, CREATION_TIME, DESCRIPTION, EXPENSE_GROUP_ID, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46000e', 1000.0, '2022-07-07 13:42:21.328452', 'Pera expense description', '46f8f3fa-e285-4895-8b9f-29391c46321f', 'a8e2f208-b667-44e7-ae3a-9d0e044658b2');

INSERT INTO expense (ID, AMOUNT, CREATION_TIME, DESCRIPTION, EXPENSE_GROUP_ID, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46001e', 1000.0, '2022-07-07 13:42:21.328452', 'Pera expense description', '46f8f3fa-e285-4895-8b9f-29391c46321f', 'a8e2f208-b667-44e7-ae3a-9d0e044658b2');

INSERT INTO expense (ID, AMOUNT, CREATION_TIME, DESCRIPTION, EXPENSE_GROUP_ID, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46002e', 500.0, '2022-07-07 13:42:21.328452', 'Mika expense description', '57f8f3fa-e285-4895-8b9f-29391c46432e', 'a8e2f208-b667-44e7-ae3a-9d0e044658b3');

INSERT INTO expense (ID, AMOUNT, CREATION_TIME, DESCRIPTION, EXPENSE_GROUP_ID, USER_ID)
VALUES ('46f8f3fa-e285-4895-8b9f-29391c46003e', 300.0, '2022-07-07 13:42:21.328452', 'Mika expense description', '57f8f3fa-e285-4895-8b9f-29391c46432e', 'a8e2f208-b667-44e7-ae3a-9d0e044658b3');