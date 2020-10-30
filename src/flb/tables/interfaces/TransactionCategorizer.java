package flb.tables.interfaces;

import flb.components.CategoryMenuImpl;

public interface TransactionCategorizer {
    void userCategorizesTransaction();
    void addCategorizingListener(CategoryMenuImpl categoryMenuImpl);
}
