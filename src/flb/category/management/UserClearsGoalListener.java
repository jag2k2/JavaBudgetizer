package flb.category.management;

import flb.category.CategoryStorage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserClearsGoalListener(CategoryStorage categoryStorage, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToClear = categoryTable.getSelectedRowName();
        categoryStorage.updateAmount(categoryToClear, Float.NaN);
        categoryTable.refresh(categoryStorage.getCategories(""));
    }
}
