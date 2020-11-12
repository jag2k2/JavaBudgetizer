package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserFiltersCategoriesListenerTest {
    private JTextField nameFilter;
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        CategoryEditorImpl categoryEditor = new CategoryEditorImpl(categoryStore);
        this.tableAutomator = categoryEditor.getTableTester();
        this.nameFilter = new JTextField();

        this.expected = new ArrayList<>();

        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void removeUpdate() {
        String filterString = "Name";
        nameFilter.setText(filterString);
        for (Category category : TestDatabase.getTestCategories()){
            if(category.getName().contains(filterString)){
                expected.add(category);
            }
        }

        assertEquals(expected, tableAutomator.getContents());
    }
}