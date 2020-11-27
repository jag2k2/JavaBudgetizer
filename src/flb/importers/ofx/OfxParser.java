package flb.importers.ofx;

import flb.util.*;
import flb.tuples.*;
import org.w3c.dom.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OfxParser {
    public enum AccountType { CHECKING, CREDIT }

    static public Transactions<Transaction> parseTransactions(Document document){
        Transactions<Transaction> transactions = new TransactionsImpl<>();
        Maybe<AccountType> accountType = getAccountType(document);

        for(AccountType type : accountType){
            NodeList transactionNodes = document.getElementsByTagName("STMTTRN");
            for (int i = 0; i < transactionNodes.getLength(); i++){
                if (transactionNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
                    Element transaction = (Element) transactionNodes.item(i);

                    String reference = transaction.getElementsByTagName("FITID").item(0).getTextContent();
                    String rawDate = transaction.getElementsByTagName("DTPOSTED").item(0).getTextContent();
                    int year = Integer.parseInt(rawDate.substring(0,4));
                    int month = Integer.parseInt(rawDate.substring(4,6))-1;
                    int day = Integer.parseInt(rawDate.substring(6,8));
                    Calendar date = new GregorianCalendar(year, month, day);
                    float amount = Float.parseFloat(transaction.getElementsByTagName("TRNAMT").item(0).getTextContent());
                    String description = transaction.getElementsByTagName("NAME").item(0).getTextContent();
                    String transactionType = transaction.getElementsByTagName("TRNTYPE").item(0).getTextContent();

                    if(type == AccountType.CREDIT){
                        transactions.add(new CreditTransaction(reference, date, description, amount, "", ""));
                    }

                    if(type == AccountType.CHECKING) {
                        int locAmtDot = reference.indexOf('.');
                        int startOffsetOfBalance = 10;
                        if (transactionType.equals("CHECK")){
                            String checkNum = transaction.getElementsByTagName("CHECKNUM").item(0).getTextContent();
                            startOffsetOfBalance += checkNum.length();
                        }
                        float balance = Float.parseFloat(reference.substring(locAmtDot + startOffsetOfBalance));
                        transactions.add(new BankingTransaction(reference, date, description, amount, "", balance));
                    }
                }
            }
        }
        return transactions;
    }

    static protected Maybe<AccountType> getAccountType(Document document) {
        NodeList nodesThatOnlyAppearsInCheckingDocument = document.getElementsByTagName("ACCTTYPE");
        NodeList nodesThatOnlyAppearsInCreditDocument = document.getElementsByTagName("CREDITCARDMSGSRSV1");
        if (nodesThatOnlyAppearsInCheckingDocument.getLength() > 0)
            return new Maybe<>(AccountType.CHECKING);
        else if (nodesThatOnlyAppearsInCreditDocument.getLength() > 0)
            return new Maybe<>(AccountType.CREDIT);
        else return new Maybe<>();
    }
}
