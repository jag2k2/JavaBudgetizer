package com.jag2k2.importers.file;

import com.jag2k2.importers.ofx.OfxParser.AccountType;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CsvFileChooserFake extends JFileChooser {
    private final AccountType accountType;

    public CsvFileChooserFake(AccountType accountType){
        this.accountType = accountType;
    }
    @Override
    public File getSelectedFile() {
        if(accountType == AccountType.CHECKING)
            return new File(".\\src\\test\\java\\com\\jag2k2\\importers\\file\\test_debit.csv");
        if(accountType == AccountType.CREDIT)
            return new File(".\\src\\test\\java\\com\\jag2k2\\importers\\file\\test_credit.csv");
        else
            return new File(".\\src\\test\\java\\com\\jag2k2\\importers\\file\\test_debit.csv");
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        return JFileChooser.APPROVE_OPTION;
    }
}