DROP TABLE IF EXISTS users;
CREATE TABLE users
(
  id                    BIGSERIAL,
  username              VARCHAR(30) NOT NULL UNIQUE,
  password              VARCHAR(80) NOT NULL,
  firstname             VARCHAR(50),
  lastname              VARCHAR(50),
  email                 VARCHAR(50) UNIQUE,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS roles;
CREATE TABLE roles 
(
  id                    BIGSERIAL,
  name                  VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles 
(
  user_id               BIGINT NOT NULL,
  role_id               BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);


INSERT INTO users (username,password) VALUES ('user', '$2a$10$Ae7P0D5X94BCWjL7PwZhNOOX.nA9Qi5KfXL76JDoOBz6avHY82tjm');
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO users_roles (user_id,role_id) VALUES (1, 1);
