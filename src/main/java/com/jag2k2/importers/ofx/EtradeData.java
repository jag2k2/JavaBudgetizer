// package com.jag2k2.importers.ofx;

// import com.webcohesion.ofx4j.client.FinancialInstitutionData;

// import java.net.URL;

// public class EtradeData implements FinancialInstitutionData{

//     @Override
//     public String getName() {
//         return "E*TRADE";
//     }

//     @Override
//     public String getFinancialInstitutionId() {
//         return "fldProv_mProvBankId";
//     }

//     @Override
//     public String getOrganization() {
//         return "fldProv_mId";
//     }

//     @Override
//     public URL getOFXURL() {
//         URL url = null;
//         try {
//             url = new URL("https://ofx.etrade.com/cgi-ofx/etradeofx");

//         } catch (Exception ex) {ex.printStackTrace();}
//         return url;
//     }

//     @Override
//     public String getId() {
//         //return "121000358";
//         return null;
//     }

//     @Override
//     public String getBrokerId() {
//         return "etrade.com";
//     }
// }
