package com.jag2k2.importers.file;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileFilterQFX extends FileFilter{

    @Override
    public boolean accept(File f) {
        String name = f.getName();
        if(f.isDirectory()){
            return true;
        }
        int i = name.lastIndexOf('.');
        if(i > 0 && i < name.length()-1) {
            String extension = name.substring(i+1).toLowerCase();
            return extension.equals("qfx");
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "QFX files";
    }
}
