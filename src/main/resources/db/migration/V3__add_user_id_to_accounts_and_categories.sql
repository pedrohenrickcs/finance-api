DELETE FROM accounts;
DELETE FROM categories;

ALTER TABLE accounts
  ADD COLUMN user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE;
CREATE INDEX accounts_user_id_idx ON accounts (user_id);

ALTER TABLE categories
  ADD COLUMN user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE categories DROP CONSTRAINT categories_name_type_unique;
ALTER TABLE categories
  ADD CONSTRAINT categories_user_name_type_unique UNIQUE (user_id, name, type);
