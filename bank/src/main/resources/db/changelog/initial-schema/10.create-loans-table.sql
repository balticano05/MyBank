CREATE TABLE loans (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    bank_account_id UUID NOT NULL,
    amount DECIMAL NOT NULL,
    currency_id UUID NOT NULL,
    interest_rate DECIMAL NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(32) NOT NULL,

    CONSTRAINT fk_loans_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_loans_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id),
    CONSTRAINT fk_loans_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(id)
);

CREATE INDEX idx_loans_user_id ON loans (user_id);
CREATE INDEX idx_loans_bank_account_id ON loans (bank_account_id);