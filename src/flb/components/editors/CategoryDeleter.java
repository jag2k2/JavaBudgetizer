package flb.components.editors;

import java.awt.*;

public interface CategoryDeleter extends CategoryListChangeRefresher {
    void userDeleteCategory(int row, Component component);
}
