package flb.application.category.listeners;

import javax.swing.*;

public interface CategoryDeleter extends ListChangeRefresher {
    void userDeleteSelectedCategory(JFrame frame);
}
