CREATE TABLE accounts (
  id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name            VARCHAR(100)  NOT NULL,
  type            VARCHAR(20)   NOT NULL,
  initial_balance NUMERIC(15,2) NOT NULL DEFAULT 0,
  created_at      TIMESTAMPTZ   NOT NULL DEFAULT now(),
  CONSTRAINT accounts_type_check CHECK (type IN ('CHECKING', 'WALLET', 'CREDIT_CARD'))
);

CREATE TABLE categories (
  id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  type       VARCHAR(20)  NOT NULL,
  created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
  CONSTRAINT categories_type_check CHECK (type IN ('INCOME', 'EXPENSE')),
  CONSTRAINT categories_name_type_unique UNIQUE (name, type)
);
