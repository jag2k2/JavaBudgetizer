package flb.importers.file;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JFileChooserMock extends JFileChooser {

    @Override
    public File getSelectedFile() {
        return new File(".\\test\\flb\\importers\\file\\test_debit_sgml.qfx");
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        return JFileChooser.APPROVE_OPTION;
    }
}
