package flb.application.category.listeners;

import flb.tables.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserAddsCategoryListenerTest {
    private TestDatabase database;
    private CategoryStoreEditor categoryStoreEditor;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JButton testButton;

    @BeforeEach
    void setUp() {
        this.expectedStored = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));

        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStoreEditor = new CategoryStoreEditorImp(database);
        CategoryTableModelImp tableModel = new CategoryTableModelImp();
        JTable table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTableImp(table, tableModel);
        CategoryAdder categoryAdder = new CategoryTableEditorImp(categoryStoreEditor, categoryTable);

        this.testButton = new JButton();
        testButton.addActionListener(new UserAddsCategoryListener(categoryAdder, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAdded() {
        nameFilter.setText("Test2");
        testButton.doClick();

        expectedStored.add(new Category("Test2", Float.NaN, false));
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void emptyNameNotAdded() {
        nameFilter.setText("");
        testButton.doClick();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void duplicateNameNotAdded() {
        nameFilter.setText("Name1");
        testButton.doClick();

        assertEquals("Name1", nameFilter.getText());
    }
}