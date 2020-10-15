/*package flb.category.application.listeners;

import flb.category.Category;
import flb.category.CategoryStorage;
import flb.category.application.CategoryTable;
import flb.category.application.CategoryTableModel;
import flb.database.TestDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDeletesCategoryListenerTest {
    private CategoryStorage categoryStorage;
    private TestDatabase database;
    private CategoryTableModel tableModel;
    private JFrame frame;
    private JTable table;
    private JTextField nameFilter;
    private final ActionEvent actionEvent = new ActionEvent(this, 0, "test");
    private ArrayList<Category> actualStored;
    private ArrayList<Category> actualDisplayed;
    private ArrayList<Category> expectedStored;
    private ArrayList<Category> expectedDisplayed;

    @BeforeEach
    void setUp() {
        expectedStored = new ArrayList<>();
        expectedDisplayed = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));
        expectedDisplayed.add(new Category("Test1", Float.NaN, false));

        database = new TestDatabase();
        database.connect();
        this.frame = new JFrame();
        this.categoryStorage = new CategoryStorage(database);
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.nameFilter = new JTextField();
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.remove(1);
        expectedDisplayed.remove(3);
        expectedDisplayed.remove(1);

        UserDeletesCategoryListener deletesListener = new DeleteListenerWithConfirmDialog(categoryStorage, categoryTable, nameFilter, frame);
        deletesListener.actionPerformed(actionEvent);

        actualStored = categoryStorage.getCategories("");
        actualDisplayed = tableModel.getContents();
        assertEquals(expectedStored, actualStored);
        assertEquals(expectedDisplayed, actualDisplayed);
        assertEquals("Name", nameFilter.getText());
    }

    static class DeleteListenerWithConfirmDialog extends UserDeletesCategoryListener {
        public DeleteListenerWithConfirmDialog(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter, JFrame frame){
            super(categoryStorage, categoryTable, nameFilter, frame);
        }

        @Override
        protected int getSelectionFromDialog(String categoryNameToDelete){
            return JOptionPane.YES_OPTION;
        }
    }
}*/