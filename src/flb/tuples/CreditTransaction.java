package flb.tuples;

import java.util.Calendar;

public class CreditTransaction extends Transaction{
    public CreditTransaction(String reference, Calendar date, String description, float amount, String categoryName){
        super(reference, date, description, amount, categoryName);
    }

    @Override
    public String getTypeString(){
        return "credit";
    }

    @Override
    public float getBalance(){
        return -1F;
    }

    @Override
    public boolean equals(Object toCompare) {
        return super.equals(toCompare);
    }
}
