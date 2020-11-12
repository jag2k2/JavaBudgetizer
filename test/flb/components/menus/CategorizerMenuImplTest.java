package flb.components.menus;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.mock.SummarySelectorMock;
import flb.components.monthselector.MonthSelectorImpl;
import flb.datastores.*;
import flb.components.editors.BankingEditorImpl;
import flb.components.editors.TransactionCategorizer;
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
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        TransactionCategorizer bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        this.categoryMenu = new CategorizerMenuImpl(categoryStore, bankingEditor);
        this.menuTester = categoryMenu;

        this.expected = new ArrayList<>();
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