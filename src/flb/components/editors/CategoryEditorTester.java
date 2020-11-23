package flb.components.editors;

import flb.components.editors.tables.CategoryTableTester;

public interface CategoryEditorTester {
    CategoryTableTester getTableTester();
    void setNameFilter(String nameText);
    String getNameFilter();
}
