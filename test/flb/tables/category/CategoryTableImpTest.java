package flb.tables.category;

import static org.junit.jupiter.api.Assertions.*;
import flb.tuples.*;
import flb.util.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CategoryTableImpTest {
    private CategoryTable categoryTable;
    private CategoryTableAutomator tableAutomator;
    private ArrayList<Category> expectedDisplay;

    @BeforeEach
    void setUp() {
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.categoryTable = categoryTableImp;
        this.tableAutomator = categoryTableImp;

        ArrayList<Category> tableContents = new ArrayList<>();
        tableContents.add(new Category("Name1", 100, false));
        tableContents.add(new Category("Name2", 200, true));
        tableContents.add(new Category("Name3", 300, false));
        tableContents.add(new Category("Test1", Float.NaN, false));

        expectedDisplay = new ArrayList<>();
        categoryTable.displayAndClearSelection(tableContents);
    }

    @Test
    void getSelectedCategory() {
        tableAutomator.setSelectedRow(-1);
        Maybe<Category> expected = new Maybe<>();
        assertEquals(expected, categoryTable.getSelectedCategory());

        tableAutomator.setSelectedRow(0);
        expected = new Maybe<>(new Category("Name1", 100, false));
        assertEquals(expected, categoryTable.getSelectedCategory());

        tableAutomator.setSelectedRow(1);
        expected = new Maybe<>(new Category("Name2", 200, true));
        assertEquals(expected, categoryTable.getSelectedCategory());

        tableAutomator.setSelectedRow(3);
        expected = new Maybe<>(new Category("Test1", Float.NaN, false));
        assertEquals(expected, categoryTable.getSelectedCategory());

        tableAutomator.setSelectedRow(4);
        expected = new Maybe<>();
        assertEquals(expected, categoryTable.getSelectedCategory());
    }

    @Test
    void refresh() {
        expectedDisplay = new ArrayList<>();

        categoryTable.displayAndClearSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());


        expectedDisplay.add(new Category("ID1", -100, true));
        expectedDisplay.add(new Category("ID2", -200, false));
        tableAutomator.setSelectedRow(1);

        categoryTable.displayAndClearSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());
    }

    @Test
    void refreshAndKeepSelection() {
        expectedDisplay = new ArrayList<>();
        tableAutomator.setSelectedRow(-1);

        categoryTable.displayAndKeepSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());

        expectedDisplay.add(new Category("ID1", -100, true));
        expectedDisplay.add(new Category("ID2", -200, false));
        tableAutomator.setSelectedRow(1);

        categoryTable.displayAndKeepSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(1, tableAutomator.getSelectedRow());
    }
}