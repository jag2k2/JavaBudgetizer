package flb.components.editors.tables.listeners;

import flb.components.menus.CategorizerMenuImpl;
import flb.components.menus.MenuDisplayer;
import flb.components.menus.MenuTester;
import flb.components.editors.mock.GoalSelectorMock;
import flb.datastores.*;
import flb.components.editors.BankingEditorImpl;
import flb.components.editors.TransactionCategorizer;
import flb.tuples.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserClicksCategoryColumnListenerTest {
    private UserClicksCategoryColumnListener userClicksCategoryColumnListener;
    private MenuTester menuTester;
    private ArrayList<String> expected;

    @BeforeEach
    void setUp() {
        TestDatabase database = new TestDatabase();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionCategorizer transactionCategorizer = new BankingEditorImpl(transactionStore, categoryStore, new GoalSelectorMock());
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, transactionCategorizer);
        MenuDisplayer menuDisplayer = new CategorizerMenuImpl(categoryStore, transactionCategorizer);
        this.menuTester = categoryMenu;
        this.userClicksCategoryColumnListener = new UserClicksCategoryColumnListener(menuDisplayer);

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