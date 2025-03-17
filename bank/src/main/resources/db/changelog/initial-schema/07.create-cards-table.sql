CREATE TABLE cards(
    id UUID PRIMARY KEY,
    bank_account_id UUID NOT NULL,
    card_number VARCHAR(19) NOT NULL,
    card_type VARCHAR(16) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_cards_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);

CREATE INDEX idx_cards_bank_account_id ON cards(bank_account_id);