package flb.listeners;

import flb.components.editors.CategoryAdder;
import javax.swing.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryAdder categoryAdder;
    private final JTextField nameFilter;

    public UserAddsCategoryListener(CategoryAdder categoryAdder, JTextField nameFilter){
        this.categoryAdder = categoryAdder;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nameToAdd = nameFilter.getText();
        if (categoryAdder.categoryNameAddable(nameToAdd)) {
            categoryAdder.userAddCategory(nameToAdd);
            nameFilter.setText("");
            categoryAdder.refreshAndClearSelection("");
        }
    }
}

