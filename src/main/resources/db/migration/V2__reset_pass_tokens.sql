DROP TABLE IF EXISTS password_reset_tokens;
CREATE TABLE password_reset_tokens
(
  id                    BIGSERIAL,
  token                 VARCHAR(50) NOT NULL,
  expiry_date           TIMESTAMP NOT NULL,
  user_id               BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);
