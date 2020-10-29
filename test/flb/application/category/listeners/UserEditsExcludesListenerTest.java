package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.tables.category.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserEditsExcludesListenerTest {
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

        CategoryExcludeEditor excludeEditor = new CategoryEditorImp(categoryStore, categoryTable);
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(excludeEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void enableDisabledCategory() {
        nameFilter.setText("Name");
        categoryTable.setSelectedRow(0);

        categoryTable.toggleSelectedExcludes();

        expected.set(0, new Category("Name1", 100, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void disableEnabledCategory() {
        nameFilter.setText("Name");
        categoryTable.setSelectedRow(1);

        categoryTable.toggleSelectedExcludes();

        expected.set(1, new Category("Name2", 200, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}