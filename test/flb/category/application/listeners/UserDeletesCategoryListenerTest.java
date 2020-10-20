package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import flb.category.application.CategoryTableEditorImp;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserDeletesCategoryListenerTest {
    private TestDatabase database;
    private CategoryStoreEditor categoryStoreEditor;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JTable table;
    private JButton testButton;
    private CategoryTableImp categoryTableImp;
    private CategoryDeleter categoryDeleter;

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
        tableModel.updateCategories(expectedStored);
        this.table = new JTable(tableModel);
        categoryTableImp = new CategoryTableImp(table, tableModel);

        this.testButton = new JButton();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDelete() {
        categoryDeleter = new TableEditorNoDialog(categoryStoreEditor, categoryTableImp, true);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        expectedStored.remove(1);
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userRefusesDelete() {
        categoryDeleter = new TableEditorNoDialog(categoryStoreEditor, categoryTableImp, false);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userDeletesWithNoSelected() {
        categoryDeleter = new CategoryTableEditorImp(categoryStoreEditor, categoryTableImp);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(-1,-1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}