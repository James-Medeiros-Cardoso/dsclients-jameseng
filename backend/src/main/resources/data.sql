INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Maiquel Knechtel Lessa', '050.755.624-52', 8850.57, TIMESTAMP WITH TIME ZONE '1986-08-27T09:10:30Z', 0, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Carlos Iran Kraey', '354.510.864-49', 5500.64, TIMESTAMP WITH TIME ZONE '1988-01-05T22:55:37Z', 1, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Cristiano Trindade Dias', '749.587.633-08', 4800.42, TIMESTAMP WITH TIME ZONE '1990-10-14T15:42:28Z', 0, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Alexandre Bassani de Jesus', '522.633.159-20', 6582.41, TIMESTAMP WITH TIME ZONE '1989-05-17T12:49:52Z', 3, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Robsom Almeida', '147.742.953-47', 7856.64, TIMESTAMP WITH TIME ZONE '1994-06-23T10:19:22Z', 2, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Cristian Machado Cardoso', '852.369.412-64', 2465.89, TIMESTAMP WITH TIME ZONE '1984-12-25T16:39:09Z', 0, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Felippe Espindola', '759.684.624-85', 4752.74, TIMESTAMP WITH TIME ZONE '1989-11-03T19:41:27Z', 1, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Cassiano Costa de Medeiros', '112.357.159-87', 1589.16, TIMESTAMP WITH TIME ZONE '1995-10-05T20:24:51Z', 2, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Amilton Prestes Jacob', '689.854.746-33', 9854.95, TIMESTAMP WITH TIME ZONE '1993-07-10T06:22:54Z', 1, NOW());
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN, created_At) VALUES ('Jorje Miguel da Silva Santos', '253.547.854-69', 2389.22, TIMESTAMP WITH TIME ZONE '1988-06-01T13:59:31Z', 2, NOW());

INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Ana', 'Maria', 'ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('James', 'Medeiros Cardoso', 'james-medeiros@hotmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 3);