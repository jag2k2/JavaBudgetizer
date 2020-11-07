package flb.components.editors;

import javax.swing.*;

public interface CategoryDeleter extends CategoryListChangeRefresher {
    void userDeleteCategory(int row, JFrame frame);
}
