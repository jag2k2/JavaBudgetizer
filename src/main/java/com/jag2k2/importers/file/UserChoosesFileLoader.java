package com.jag2k2.importers.file;

import com.jag2k2.util.Maybe;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UserChoosesFileLoader implements FileLoader {
    private final JFileChooser fileChooser;
    private final Component parentComponent;

    public UserChoosesFileLoader(JFileChooser fileChooser, Component component){
        this.fileChooser = fileChooser;
        this.parentComponent = component;

        String userhome = System.getProperty("user.home");
        fileChooser.setCurrentDirectory(new File(userhome + "\\Downloads"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilterCSV());
        fileChooser.setDialogTitle("Import Transactions");
        fileChooser.getActionMap().get("viewTypeDetails").actionPerformed(null);
    }

    @Override
    public Maybe<File> getFileToImport(){
        int selection = fileChooser.showOpenDialog(parentComponent);
        if (selection == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();
            return new Maybe<>(importFile);
        }
        else
            return new Maybe<>();
    }
}
