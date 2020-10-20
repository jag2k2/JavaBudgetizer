package flb.category.application.listeners;

import javax.swing.*;

public interface CategoryDeleter extends ListChangeRefresher {
    void userDeleteSelectedCategory(JFrame frame);
}
