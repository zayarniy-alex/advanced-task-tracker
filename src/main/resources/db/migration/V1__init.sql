DROP TABLE IF EXISTS departments;
CREATE TABLE departments
(
  id                    BIGSERIAL,
  title                 VARCHAR(250) NOT NULL UNIQUE,
  head_id               BIGINT NOT NULL,
  up_department_id      BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (up_department_id) REFERENCES departments (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
  id                    BIGSERIAL,
  login                 VARCHAR(30) NOT NULL UNIQUE,
  password              VARCHAR(80) NOT NULL,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  email                 VARCHAR(50) UNIQUE,
  department_id         BIGINT,
  position              VARCHAR(50),
  PRIMARY KEY (id),
  FOREIGN KEY (department_id) REFERENCES departments (id)
);

INSERT INTO users (login, password, first_name, last_name, position) VALUES
('user', '$2a$10$uIldifxt7lyLHtygnf5EVO8yKJagb8M.QjvbzvTjA2UOz6RzXNULu', 'Юзер', 'Тестовый', 'Junior Developer'),
('manager', '$2a$10$uIldifxt7lyLHtygnf5EVO8yKJagb8M.QjvbzvTjA2UOz6RzXNULu', 'Менеджер', 'Тестовый', 'Project Manager');

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
  id                    BIGSERIAL,
  title                 VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

INSERT INTO roles (title) VALUES ('ROLE_USER');

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
  user_id               BIGINT NOT NULL,
  role_id               BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

DROP TABLE IF EXISTS projects;
CREATE TABLE projects
(
  id                    BIGSERIAL,
  title                 VARCHAR(250) NOT NULL UNIQUE,
  description           VARCHAR(2000) NOT NULL,
  manager_id            BIGINT,
  status                VARCHAR(50),
  PRIMARY KEY (id),
  FOREIGN KEY (manager_id) REFERENCES users (id),
  CHECK (status in ('CREATED','ONGOING','COMPLETE','ARCHIVE'))
);
INSERT INTO projects (title, description, manager_id, status) VALUES
('Тестовый проект 1', 'Test project', (select id from users where login='manager'), 'CREATED'),
('Тестовый проект 2', 'Test project', (select id from users where login='manager'), 'CREATED');


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
  title                 VARCHAR(250) NOT NULL UNIQUE,
  description           VARCHAR(2000) NOT NULL,
  manager_id            BIGINT,
  employer_id           BIGINT,
  start_time            DATE default current_date,
  due_time              DATE,
  urgency               VARCHAR(50),
  status                VARCHAR(50),
  progress              DECIMAL,
  project_id            BIGINT,
  plan_time             DECIMAL,
  PRIMARY KEY (id),
  FOREIGN KEY (manager_id) REFERENCES users (id),
  FOREIGN KEY (employer_id) REFERENCES users (id),
  FOREIGN KEY (project_id) REFERENCES projects (id),
  CHECK (status in ('CREATED','ONGOING','COMPLETE','ARCHIVE')),
  CHECK (urgency in ('HIGH','AVERAGE','LOW'))
);

DROP TABLE IF EXISTS task_history;
CREATE TABLE task_history
(
  id                    BIGSERIAL,
  task_id               BIGINT NOT NULL,
  change_date           DATE default current_date,
  description           VARCHAR(2000) NOT NULL,
  user_id               BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (task_id) REFERENCES tasks (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS documents;
CREATE TABLE documents
(
  id                    BIGSERIAL,
  title                 VARCHAR(250) NOT NULL,
  description           VARCHAR(2000),
  data                  BYTEA,
  object_id             BIGINT,
  object_type           VARCHAR(50),
  date_create			DATE default current_date,
  PRIMARY KEY (id),
  CHECK (object_type in ('TASK','PROJECT','DEPARTMENT','USER','COMMENT','NOTIFICATION'))
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments
(
  id                    BIGSERIAL,
  data                  VARCHAR(2000),
  author_id             BIGINT,
  date_create           DATE default current_date,
  task_id 				BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (author_id) REFERENCES users (id),
  FOREIGN KEY (task_id ) REFERENCES tasks (id)
);

DROP TABLE IF EXISTS notifications;
CREATE TABLE notifications
(
  id                    BIGSERIAL,
  status                VARCHAR(50) NOT NULL,
  data                  VARCHAR(2000) NOT NULL,
  date_create           DATE NOT NULL,
  task_id 				BIGINT,
  project_id 			BIGINT,
  receiver_id 			BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (receiver_id) REFERENCES users (id),
  FOREIGN KEY (task_id ) REFERENCES tasks (id),
  FOREIGN KEY (project_id ) REFERENCES projects (id)
);

DROP TABLE IF EXISTS users_notifications;
CREATE TABLE users_notifications
(
  notification_id       BIGINT,
  receiver_id           BIGINT,
  status                VARCHAR(50) default 'NOT_READ',
  PRIMARY KEY (notification_id,receiver_id),
  FOREIGN KEY (notification_id) REFERENCES notifications (id),
  FOREIGN KEY (receiver_id) REFERENCES users (id),
  CHECK (status in ('NOT_READ','READ'))
);

DROP TABLE IF EXISTS task_time;
CREATE TABLE task_time
(
  id                    BIGSERIAL,
  date_start			DATE default current_date,
  date_finish 			DATE,
  time_elapsed          DECIMAL,
  task_id 				BIGINT,
  user_id               BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (task_id ) REFERENCES tasks (id)
);