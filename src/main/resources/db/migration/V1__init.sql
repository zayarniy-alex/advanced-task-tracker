DROP TABLE IF EXISTS users;
CREATE TABLE users
(
  id                    BIGSERIAL,
  login                 VARCHAR(30) NOT NULL UNIQUE,
  password              VARCHAR(80) NOT NULL,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  email                 VARCHAR(50) UNIQUE,
  department_id         BIGSERIAL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
  id                    BIGSERIAL,
  title                 VARCHAR(50) NOT NULL UNIQUE,
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

INSERT INTO users (login, password) VALUES ('user', '$2a$10$Ae7P0D5X94BCWjL7PwZhNOOX.nA9Qi5KfXL76JDoOBz6avHY82tjm');
INSERT INTO roles (title) VALUES ('ROLE_USER');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

DROP TABLE IF EXISTS departments;
CREATE TABLE departments
(
  id                    BIGSERIAL,
  title                 VARCHAR(30) NOT NULL UNIQUE,
  head_id               BIGINT NOT NULL,
  up_department_id      BIGINT NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS projects;
CREATE TABLE projects
(
  id                    BIGSERIAL,
  title                 VARCHAR(30) NOT NULL UNIQUE,
  description           VARCHAR(80) NOT NULL,
  manager_id            BIGSERIAL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_projects;
CREATE TABLE users_projects
(
  user_id               BIGINT NOT NULL,
  project_id            BIGINT NOT NULL,
  PRIMARY KEY (user_id, project_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (project_id) REFERENCES projects (id)
);

DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks
(
  id                    BIGSERIAL,
  title                 VARCHAR(30) NOT NULL UNIQUE,
  description           VARCHAR(80) NOT NULL,
  manager_id            BIGSERIAL,
  employer_id           BIGSERIAL,
  start_time            DATE,
  due_time              DATE,
  urgency               VARCHAR(255),
  status                VARCHAR(255),
  progress              DECIMAL,
  project_id            BIGSERIAL,
  plan_time             BIGSERIAL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS task_history;
CREATE TABLE task_history
(
  id                    BIGSERIAL,
  task_id               BIGSERIAL,
  change_date           DATE,
  description           VARCHAR(80) NOT NULL,
  user_id               BIGSERIAL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS documents;
CREATE TABLE documents
(
  id                    BIGSERIAL,
  title                 VARCHAR(30) NOT NULL UNIQUE,
  description           VARCHAR(80) NOT NULL,
  data                  VARCHAR(255),
  object_id             BIGSERIAL,
  object_type           VARCHAR(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments
(
  id                    BIGSERIAL,
  data                  VARCHAR(255),
  object_type           VARCHAR(255),
  object_id             BIGSERIAL,
  author_id             BIGSERIAL,
  date_create           DATE,
  comment_type          VARCHAR(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS notifications;
CREATE TABLE notifications
(
  id                    BIGSERIAL,
  comment_id            BIGSERIAL,
  receiver_id           BIGSERIAL,
  status                DATE,
  comment_type          VARCHAR(30),
  PRIMARY KEY (id)
);