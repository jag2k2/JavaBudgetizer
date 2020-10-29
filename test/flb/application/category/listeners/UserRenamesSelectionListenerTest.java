package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.tables.category.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserRenamesSelectionListenerTest {
    private JTextField nameFilter;
    private ArrayList<Category> expected;
    private CategoryTable categoryTable;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));
        this.categoryTable = new CategoryTableImp();
        categoryTable.displayAndClearSelection(expected);

        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);

        CategoryNameEditor nameEditor = new CategoryEditorImp(categoryStore, categoryTable);
        categoryTable.addCategoryRenameListener(new UserRenamesSelectionListener(nameEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void renameFirstCategory() {
        nameFilter.setText("Name");
        categoryTable.setSelectedRow(0);

        categoryTable.editCellAt(0,0);
        categoryTable.setEditorName("Name10");

        expected.set(0, new Category("Name10", 100, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void renameLastCategory() {
        nameFilter.setText("Name");
        categoryTable.setSelectedRow(4);

        categoryTable.editCellAt(4,0);
        categoryTable.setEditorName("Test20");

        expected.set(4, new Category("Test20", 500, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}