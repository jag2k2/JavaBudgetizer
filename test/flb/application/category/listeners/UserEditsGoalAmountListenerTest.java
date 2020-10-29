package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import flb.tables.category.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserEditsGoalAmountListenerTest {
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

        CategoryAmountEditor amountEditor = new CategoryEditorImp(categoryStore, categoryTable);
        categoryTable.addGoalEditListener(new UserEditsGoalAmountListener(amountEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editCategoryGoal() {
        nameFilter.setText("Name");
        categoryTable.setSelectedRow(0);

        categoryTable.editCellAt(0,1);
        categoryTable.setEditorGoal(200);

        expected.set(0, new Category("Name1", 200, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}