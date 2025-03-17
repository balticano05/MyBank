CREATE TABLE exchange_rates (
    id UUID PRIMARY KEY,
    base_currency_id UUID NOT NULL,
    target_currency_id UUID NOT NULL,
    rate DECIMAL NOT NULL,
    actual_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_exchange_rates_base_currency_id FOREIGN KEY (base_currency_id) REFERENCES currencies(id),
    CONSTRAINT fk_exchange_rates_target_currency_id FOREIGN KEY (target_currency_id) REFERENCES currencies(id)
);

CREATE UNIQUE INDEX idx_exchange_rates_unique ON exchange_rates (base_currency_id, target_currency_id, actual_date);