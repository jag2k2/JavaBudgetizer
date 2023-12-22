package com.jag2k2.importers.file;

import com.jag2k2.util.Maybe;
import java.io.*;

public interface FileLoader {
    Maybe<File> getFileToImport();
}
