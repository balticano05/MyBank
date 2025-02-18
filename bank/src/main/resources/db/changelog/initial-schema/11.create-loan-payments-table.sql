CREATE TABLE loan_payments (
    id UUID PRIMARY KEY,
    loan_id UUID NOT NULL,
    payment_amount DECIMAL NOT NULL,
    payment_date DATE NOT NULL,
    status VARCHAR(32) NOT NULL,

    CONSTRAINT fk_loan_payments_loan_id FOREIGN KEY (loan_id) REFERENCES loans(id)
);

CREATE INDEX idx_loan_payments_loan_id ON loan_payments (loan_id);