CREATE TABLE bank_accounts (
    id UUID PRIMARY KEY,
    title VARCHAR(64) UNIQUE NOT NULL,
    owner_id UUID NOT NULL,
    created_at DATE,
    balance DECIMAL NOT NULL DEFAULT 0,
    status VARCHAR(32) NOT NULL,
    currency_id UUID NOT NULL,

    CONSTRAINT fk_bank_accounts_owner_id FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_bank_accounts_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(id)
);

CREATE INDEX idx_bank_accounts_owner_id ON bank_accounts(owner_id);