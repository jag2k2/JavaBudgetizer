// package com.jag2k2.importers.ofx;

// import com.webcohesion.ofx4j.client.*;
// import com.webcohesion.ofx4j.client.impl.FinancialInstitutionServiceImpl;
// import com.webcohesion.ofx4j.domain.data.banking.AccountType;
// import com.webcohesion.ofx4j.domain.data.banking.BankAccountDetails;
// import com.webcohesion.ofx4j.domain.data.investment.accounts.InvestmentAccountDetails;

// import java.util.Date;
// import java.util.Locale;

// public class OfxEtradeSandbox {
//     public static void main(String[] args) {
//         FinancialInstitutionData data = new EtradeData();
//         FinancialInstitutionService service = new FinancialInstitutionServiceImpl();
//         FinancialInstitution financialInstitution = service.getFinancialInstitution(data);
//         financialInstitution.setLanguage(Locale.US.getISO3Language().toUpperCase());

//         try {
//             FinancialInstitutionProfile profile = financialInstitution.readProfile();
//             System.out.println(profile.getCity());

//             InvestmentAccountDetails accountDetails = new InvestmentAccountDetails();
//             accountDetails.setAccountNumber("6107-0001");
//             accountDetails.setBrokerId("etrade.com");

//             InvestmentAccount account = financialInstitution.loadInvestmentAccount(accountDetails, "jag2k2_etr", "stockS1229");
//             System.out.println(account.getDetails().getAccountKey());

//             /*Date startDate = new Date(2020, 8, 01);
//             Date endDate = new Date(2020, 10, 30);

//             AccountStatement statement = account.readStatement(startDate, endDate);

//             System.out.println(statement);*/

//         } catch (Exception ex) {ex.printStackTrace();}
//     }
// }
