package flb.components.menus;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.components.editor.summary.SummarySelectorMock;
import flb.components.monthselector.MonthSelectorImpl;
import flb.databases.*;
import flb.datastores.*;
import flb.components.editor.transaction.banking.BankingEditorImpl;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.tuples.*;
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
        BankingStore bankingStore = new DataStoreImpl(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        TransactionCategorizer bankingEditor = new BankingEditorImpl(bankingStore, monthSelectorImpl, new SummarySelectorMock());
        categoryMenu = new CategorizerMenuImpl(bankingStore.getCategories(""), bankingEditor);
        menuTester = categoryMenu;

        expected = new ArrayList<>();
        for (Category category : CategoryListFactory.makeDefaultCategories()) {
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