CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    from_account_id UUID,
    to_account_id UUID NOT NULL,
    amount DECIMAL NOT NULL,
    currency_id UUID NOT NULL,
    transaction_type VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,

    CONSTRAINT fk_transactions_from_account_id FOREIGN KEY(from_account_id) REFERENCES bank_accounts(id),
    CONSTRAINT fk_transactions_to_account_id FOREIGN KEY(to_account_id) REFERENCES bank_accounts(id),
    CONSTRAINT fk_transactions_currency_id FOREIGN KEY(currency_id) REFERENCES currencies(id)
);

CREATE INDEX idx_transactions_from_account_id ON transactions (from_account_id);
CREATE INDEX idx_transactions_to_account_id ON transactions (to_account_id);