package com.jag2k2.listeners;

import com.jag2k2.components.editor.category.CategoryAdder;

import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryAdder categoryAdder;

    public UserAddsCategoryListener(CategoryAdder categoryAdder){
        this.categoryAdder = categoryAdder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        categoryAdder.addCategory();
    }
}

