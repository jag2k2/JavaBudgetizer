package flb.components.categoryselector;

import static org.junit.jupiter.api.Assertions.*;

import flb.datastores.*;
import flb.components.editors.BankingEditorImpl;
import flb.components.editors.TransactionCategorizer;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CategoryMenuImplTest {
    private TestDatabase database;
    private CategoryMenuImpl categoryMenu;
    private MenuTester menuTester;
    private List<String> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionStore transactionStore = new TransactionStoreImp(database);
        TransactionCategorizer bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database));
        this.categoryMenu = new CategoryMenuImpl(categoryStore, bankingEditor);
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