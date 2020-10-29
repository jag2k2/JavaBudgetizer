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
    private CategoryTableAutomator tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.tableAutomator = categoryTableImp;

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));
        ((CategoryTable) categoryTableImp).displayAndClearSelection(expected);

        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);

        CategoryExcludeEditor excludeEditor = new CategoryEditorImp(categoryStore, categoryTableImp);
        ((CategoryTable) categoryTableImp).addExcludesEditListener(new UserEditsExcludesListener(excludeEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void enableDisabledCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(0);

        tableAutomator.toggleSelectedExcludes();

        expected.set(0, new Category("Name1", 100, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void disableEnabledCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);

        tableAutomator.toggleSelectedExcludes();

        expected.set(1, new Category("Name2", 200, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}