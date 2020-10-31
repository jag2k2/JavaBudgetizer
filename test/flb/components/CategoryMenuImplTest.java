package flb.components;

import static org.junit.jupiter.api.Assertions.*;;
import flb.database.interfaces.*;
import flb.database.*;
import flb.tables.banking.*;
import flb.tables.credit.*;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CategoryMenuImplTest {
    private TestDatabase database;
    private CategoryMenuImpl categoryMenu;


    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        TransactionStore transactionStore = new TransactionStoreImp(database);
        BankingEditorImpl bankingEditor = new BankingEditorImpl(transactionStore);
        CreditEditorImpl creditEditor = new CreditEditorImpl(transactionStore);
        this.categoryMenu = new CategoryMenuImpl(categoryStore, transactionStore, bankingEditor, creditEditor);
    }

    @AfterEach
    void tearDown() { this.database.close();}

    @Test
    void buildMenu() {
        ArrayList<String> expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()) {
            expected.add(category.getName());
        }

        categoryMenu.buildMenu("banking", 0);

        assertEquals(expected, categoryMenu.toStringArray());
    }
}