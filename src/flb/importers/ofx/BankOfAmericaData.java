package flb.importers.ofx;

import com.webcohesion.ofx4j.client.*;

import java.net.URL;

public class BankOfAmericaData implements FinancialInstitutionData{

    @Override
    public String getFinancialInstitutionId() {
        return "5959";
    }

    @Override
    public String getName() {
        return "Bank of America";
    }

    @Override
    public String getOrganization() {
        return "HAN";
    }

    @Override
    public URL getOFXURL() {
        URL url = null;
        try {
            url = new URL("https://eftx.bankofamerica.com/eftxweb/access.ofx");

        } catch (Exception ex) {ex.printStackTrace();}
        return url;
    }

    @Override
    public String getId() {
        return "121000358";
    }

    @Override
    public String getBrokerId() {
        return null;
    }
}
