DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id       INT(8) PRIMARY KEY AUTO_INCREMENT,
    role_name NVARCHAR(64) NOT NULL
);
INSERT INTO role(role_name)
VALUES ('ADMIN'),
       ('USER'),
       ('GUEST')