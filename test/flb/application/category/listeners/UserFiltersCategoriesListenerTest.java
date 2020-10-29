package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.tables.category.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserFiltersCategoriesListenerTest {
    private JTextField nameFilter;
    private ArrayList<Category> expected;
    private CategoryTableAutomator tableAutomator;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.tableAutomator = categoryTableImp;
        this.nameFilter = new JTextField();

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1", Float.NaN, false));

        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);

        ListChangeRefresher listChangeRefresher = new CategoryEditorImp(categoryStore, categoryTableImp);
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(listChangeRefresher, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void insertUpdate() {
        nameFilter.setText("N");
        expected.remove(3);
        assertEquals(expected, tableAutomator.getContents());
    }

    @Test
    void removeUpdate() {
        nameFilter.setText("Name1");
        nameFilter.setText("Name");
        expected.remove(3);
        assertEquals(expected, tableAutomator.getContents());
    }
}