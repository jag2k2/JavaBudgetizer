package flb.importers.file;

import flb.importers.ofx.OfxParser.AccountType;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JFileChooserMock extends JFileChooser {
    private final AccountType accountType;

    public JFileChooserMock(AccountType accountType){
        this.accountType = accountType;
    }

    @Override
    public File getSelectedFile() {
        if(accountType == AccountType.CHECKING)
            return new File(".\\test\\flb\\importers\\file\\test_debit_sgml.qfx");
        if(accountType == AccountType.CREDIT)
            return new File(".\\test\\flb\\importers\\file\\test_credit_sgml.qfx");
        else
            return new File(".\\test\\flb\\importers\\file\\test_debit_sgml.qfx");
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        return JFileChooser.APPROVE_OPTION;
    }
}
