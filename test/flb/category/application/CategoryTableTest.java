package flb.category.application;

import flb.category.Category;
import flb.util.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTableTest {
    private CategoryTable categoryTable;
    private JTable table;
    private CategoryTableModel tableModel;
    private Maybe<Category> expected;
    private Maybe<Category> actual;
    ArrayList<Category> expectedDisplay;

    @BeforeEach
    void setUp() {
        ArrayList<Category> modelContents = new ArrayList<>();
        modelContents.add(new Category("Name1", 100, false));
        modelContents.add(new Category("Name2", 200, true));
        modelContents.add(new Category("Name3", 300, false));
        modelContents.add(new Category("Test1", Float.NaN, false));

        expectedDisplay = new ArrayList<>();
        tableModel = new CategoryTableModel(modelContents);
        table = new JTable(tableModel);
        categoryTable = new CategoryTable(table, tableModel);
    }

    @Test
    void getSelectedCategory() {
        table.getSelectionModel().setSelectionInterval(-1,-1);
        expected = new Maybe<>();
        actual = categoryTable.getSelectedCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(0,0);
        expected = new Maybe<>(new Category("Name1", 100, false));
        actual = categoryTable.getSelectedCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(1,1);
        expected = new Maybe<>(new Category("Name2", 200, true));
        actual = categoryTable.getSelectedCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(3,3);
        expected = new Maybe<>(new Category("Test1", Float.NaN, false));
        actual = categoryTable.getSelectedCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(4,4);
        expected = new Maybe<>();
        actual = categoryTable.getSelectedCategory();
        assertEquals(expected, actual);
    }

    @Test
    void getEditingCategory() {
        expected = new Maybe<>();
        actual = categoryTable.getEditingCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(0,0);
        expected = new Maybe<>();
        actual = categoryTable.getEditingCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(0,0);
        table.editCellAt(0,0);
        expected = new Maybe<>(new Category("Name1", 100, false));
        actual = categoryTable.getEditingCategory();
        assertEquals(expected, actual);

        table.getSelectionModel().setSelectionInterval(1,1);
        table.editCellAt(1,2);
        expected = new Maybe<>(new Category("Name2", 200, true));
        actual = categoryTable.getEditingCategory();
        assertEquals(expected, actual);
    }

    @Test
    void refresh() {
        expectedDisplay = new ArrayList<>();
        categoryTable.refresh(expectedDisplay);
        assertEquals(expectedDisplay, tableModel.getContents());
        assertEquals(-1, table.getSelectedRow());

        expectedDisplay.add(new Category("ID1", -100, true));
        expectedDisplay.add(new Category("ID2", -200, false));
        table.getSelectionModel().setSelectionInterval(1,1);
        categoryTable.refresh(expectedDisplay);
        assertEquals(expectedDisplay, tableModel.getContents());
        assertEquals(-1, table.getSelectedRow());

        expectedDisplay = new ArrayList<>();
        categoryTable.refresh(expectedDisplay);
        assertEquals(expectedDisplay, tableModel.getContents());
        assertEquals(-1, table.getSelectedRow());
    }

    @Test
    void refreshAndKeepSelection() {
        expectedDisplay = new ArrayList<>();
        categoryTable.refreshAndKeepSelection(expectedDisplay);
        assertEquals(expectedDisplay, tableModel.getContents());
        assertEquals(-1, table.getSelectedRow());

        expectedDisplay.add(new Category("ID1", -100, true));
        expectedDisplay.add(new Category("ID2", -200, false));
        table.getSelectionModel().setSelectionInterval(1,1);
        categoryTable.refreshAndKeepSelection(expectedDisplay);
        assertEquals(expectedDisplay, tableModel.getContents());
        assertEquals(1, table.getSelectedRow());
    }
}