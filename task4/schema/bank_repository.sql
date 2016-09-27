CREATE SEQUENCE customer_id;

CREATE TABLE customer
(
    ID INT DEFAULT NEXTVAL('customer_id') PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Balance INT NOT NULL
);

CREATE TABLE balance_history
(
    ID INT NOT NULL,
    Operation_date TIMESTAMP,
    Name VARCHAR(50) NOT NULL,
    Operation VARCHAR(50) NOT NULL,
    Amount INT NOT NULL
);

CREATE OR REPLACE FUNCTION logfunc() RETURNS TRIGGER AS $example_table$
    DECLARE
            diff int;

    BEGIN
            IF old.balance > new.balance THEN
            diff = old.balance - new.balance;
            INSERT INTO balance_history(ID, Operation_date, Name, Operation, Amount) VALUES(old.id, CURRENT_TIMESTAMP, old.name,'Withdraw', diff);
            ELSE
            diff = new.balance - old.balance;
            INSERT INTO balance_history(ID, Operation_date, Name, Operation, Amount) VALUES(old.id, CURRENT_TIMESTAMP, old.name,'Deposit', diff);
            END IF;
            RETURN NEW;
    END;
$example_table$ LANGUAGE plpgsql;

CREATE TRIGGER tr_tblBalance_ForUpdate AFTER UPDATE ON customer FOR EACH ROW EXECUTE PROCEDURE logfunc();