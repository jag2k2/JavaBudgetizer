package flb.tuples;

import java.util.Calendar;

public class CreditTransaction extends Transaction{
    private String payGroup;

    public CreditTransaction(String reference, Calendar date, String description, float amount, String categoryName, String payGroup){
        super(reference, date, description, amount, categoryName);
        this.payGroup = payGroup;
    }

    @Override
    public String getTypeString(){
        return "credit";
    }

    @Override
    public String getPayGroup() {
        return payGroup;
    }

    @Override
    public float getBalance(){
        return -1F;
    }

    @Override
    public String toString(){
        return super.toString() + " | " + payGroup;
    }

    @Override
    public boolean equals(Object toCompare) {
        return super.equals(toCompare) && payGroup.equals(((CreditTransaction) toCompare).payGroup);
    }
}
