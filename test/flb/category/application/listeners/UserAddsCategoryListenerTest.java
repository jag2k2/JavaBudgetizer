package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import static org.junit.jupiter.api.Assertions.*;

class UserAddsCategoryListenerTest {
    private CategoryStorage categoryStorage;
    private TestDatabase database;
    private CategoryTableModel tableModel;
    private UserAddsCategoryListener addsListener;
    private JTextField nameFilter;
    private final ActionEvent actionEvent = new ActionEvent(this, 0, "test");
    private ArrayList<Category> actualCategories;
    private ArrayList<Category> expectedCategories;

    @BeforeEach
    void init() {
        expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category("Name1", 100, false));
        expectedCategories.add(new Category("Name2", 200, true));
        expectedCategories.add(new Category("Name3", 300, false));
        expectedCategories.add(new Category("Test1", Float.NaN, false));
        actualCategories = new ArrayList<>();

        database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.nameFilter = new JTextField();
        JTable table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        this.addsListener = new UserAddsCategoryListener(categoryStorage, categoryTable, nameFilter);
    }

    @Test
    void addingCategoryWithNoName() {
        nameFilter.setText("");

        addsListener.actionPerformed(actionEvent);

        actualCategories = categoryStorage.getCategories("");
        assertEquals(expectedCategories, actualCategories);
        assertEquals(expectedCategories, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void addingNewCategory() {
        nameFilter.setText("Test2");
        expectedCategories.add(new Category("Test2", Float.NaN, false));

        addsListener.actionPerformed(actionEvent);

        actualCategories = categoryStorage.getCategories("");
        assertEquals(expectedCategories, actualCategories);
        assertEquals(expectedCategories, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void addingDuplicateCategory() {
        nameFilter.setText("Name1");

        addsListener.actionPerformed(actionEvent);

        actualCategories = categoryStorage.getCategories("");
        assertEquals(expectedCategories, actualCategories);
        assertEquals(expectedCategories, tableModel.getContents());
        assertEquals("Name1", nameFilter.getText());
    }

    @AfterEach
    void close() {
        database.close();
    }
}