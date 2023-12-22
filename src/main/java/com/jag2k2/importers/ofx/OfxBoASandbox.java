// package com.jag2k2.importers.ofx;

// import com.webcohesion.ofx4j.client.*;
// import com.webcohesion.ofx4j.client.impl.*;
// import com.webcohesion.ofx4j.domain.data.banking.AccountType;
// import com.webcohesion.ofx4j.domain.data.banking.BankAccountDetails;

// import java.util.*;

// public class OfxBoASandbox {
//     public static void main(String[] args) {
//         FinancialInstitutionData data = new BankOfAmericaData();
//         FinancialInstitutionService service = new FinancialInstitutionServiceImpl();
//         FinancialInstitution financialInstitution = service.getFinancialInstitution(data);
//         financialInstitution.setLanguage(Locale.US.getISO3Language().toUpperCase());

//         try {
//             FinancialInstitutionProfile profile = financialInstitution.readProfile();
//             System.out.println(profile.getCity());
//             BankAccountDetails bankAccountDetails = new BankAccountDetails();
//             bankAccountDetails.setRoutingNumber("111000025");
//             bankAccountDetails.setAccountNumber("005770445482");
//             bankAccountDetails.setAccountType(AccountType.SAVINGS);

//             BankAccount account = financialInstitution.loadBankAccount(bankAccountDetails, "jag2k2", "moneY1229");

//             Date startDate = new Date(2020, 11, 1);
//             Date endDate = new Date(2020, 11, 21);

//             AccountStatement statement = account.readStatement(startDate, endDate);

//             System.out.println(statement);

//         } catch (Exception ex) {ex.printStackTrace();}
//     }
// }
