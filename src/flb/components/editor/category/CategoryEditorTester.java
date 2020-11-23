package flb.components.editor.category;

public interface CategoryEditorTester {
    CategoryTableTester getTableTester();
    void setNameFilter(String nameText);
    String getNameFilter();
}
