package flb.importers.file;

import flb.util.Maybe;
import java.io.*;

public interface FileLoader {
    Maybe<File> getFileToImport();
}
