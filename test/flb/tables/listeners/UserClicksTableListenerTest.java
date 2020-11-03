package flb.tables.listeners;

import flb.components.*;
import flb.components.interfaces.*;
import flb.database.*;
import flb.database.interfaces.*;
import flb.tables.banking.*;
import flb.tables.interfaces.*;
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
        TransactionCategorizer transactionCategorizer = new BankingEditorImpl(transactionStore, categoryStore);
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