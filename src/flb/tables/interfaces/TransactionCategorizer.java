package flb.tables.interfaces;

import flb.components.*;

public interface TransactionCategorizer {
    void addCategoryColumnClickedListener(CategoryMenuImpl categoryMenuImpl);
    void userCategorizesTransaction(int row, String categoryName);
}
