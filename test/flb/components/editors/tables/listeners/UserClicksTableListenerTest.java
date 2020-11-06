package flb.components.editors.tables.listeners;

import flb.components.categorizer.CategoryMenuImpl;
import flb.components.categorizer.MenuDisplayer;
import flb.components.categorizer.MenuTester;
import flb.components.editors.mock.GoalSelectorMock;
import flb.datastores.*;
import flb.components.editors.BankingEditorImpl;
import flb.components.editors.TransactionCategorizer;
import flb.tuples.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserClicksTableListenerTest {
    private UserClicksTableListener userClicksTableListener;
    private MenuTester menuTester;
    private ArrayList<String> expected;

    @BeforeEach
    void setUp() {
        TestDatabase database = new TestDatabase();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionCategorizer transactionCategorizer = new BankingEditorImpl(transactionStore, categoryStore, new GoalSelectorMock());
        CategoryMenuImpl categoryMenu = new CategoryMenuImpl(categoryStore, transactionCategorizer);
        MenuDisplayer menuDisplayer = new CategoryMenuImpl(categoryStore, transactionCategorizer);
        this.menuTester = categoryMenu;
        this.userClicksTableListener = new UserClicksTableListener(menuDisplayer);

        this.expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()) {
            expected.add(category.getName());
        }
    }

    /*@Test
    void mousePressed() {
        MouseEvent mouseEvent = new MouseEvent(new JTable(), 0, 0, 0, 10, 20, 1, true);
        userClicksTableListener.mousePressed(mouseEvent);

        assertEquals(expected, menuTester.toStringArray());
    }*/
}