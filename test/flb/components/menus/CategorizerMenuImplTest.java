package flb.components.menus;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editor.summary.SummarySelectorMock;
import flb.components.monthselector.MonthSelectorImpl;
import flb.databases.TestDatabase;
import flb.datastores.*;
import flb.components.editor.transaction.banking.BankingEditorImpl;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CategorizerMenuImplTest {
    private TestDatabase database;
    private CategorizerMenuImpl categoryMenu;
    private MenuTester menuTester;
    private List<String> expected;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        TransactionCategorizer bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        categoryMenu = new CategorizerMenuImpl(categoryStore, bankingEditor);
        menuTester = categoryMenu;

        expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()) {
            expected.add(category.getName());
        }
    }

    @AfterEach
    void tearDown() { this.database.close();}

    @Test
    void buildMenuForBanking() {
        categoryMenu.buildMenu(0);

        assertEquals(expected, menuTester.toStringArray());
    }
}