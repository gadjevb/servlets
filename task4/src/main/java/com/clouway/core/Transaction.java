package com.clouway.core;

import java.sql.Timestamp;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Transaction {
    public final Integer id;
    public final Timestamp operationDate;
    public final String customerName;
    public final String operationType;
    public final Integer amount;

    public Transaction(Integer id, Timestamp operationDate, String customerName, String operationType, Integer amount) {
        this.id = id;
        this.operationDate = operationDate;
        this.customerName = customerName;
        this.operationType = operationType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return id + " | " + operationDate + " | " + customerName + " | " + operationType + " | " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (operationDate != null ? !operationDate.equals(that.operationDate) : that.operationDate != null)
            return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (operationType != null ? !operationType.equals(that.operationType) : that.operationType != null)
            return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operationDate != null ? operationDate.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
