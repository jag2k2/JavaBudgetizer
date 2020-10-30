package flb.components;

import flb.database.interfaces.CategoryStore;
import flb.tuples.Category;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CategoryMenuImpl {
    private final JPopupMenu mainMenu;
    private final CategoryStore categoryStore;

    public CategoryMenuImpl(CategoryStore categoryStore) {
        this.mainMenu = new JPopupMenu();
        this.categoryStore = categoryStore;
    }

    public JPopupMenu getPopup() {
        return mainMenu;
    }

    public void show(Component component, int x, int y) {
        buildMenu();
        mainMenu.show(component, x, y);
    }

    protected void buildMenu() {
        mainMenu.removeAll();
        JMenu superCategory = new JMenu("");
        for (Category category : categoryStore.getCategories("")){
            String categoryName = category.getName();
            if(categoryName.contains("::")){
                String[] elements = categoryName.split("::");
                String superCategoryName = elements[0];
                String subCategoryName = elements[1];
                if (!superCategory.getText().equals(superCategoryName)){
                    superCategory = new JMenu(superCategoryName);
                    mainMenu.add(superCategory);
                }
                superCategory.add(new JMenuItem(subCategoryName));
            }
            else{
                mainMenu.add(new JMenuItem(categoryName));
            }
        }
    }

    protected ArrayList<String> toStringArray() {
        ArrayList<String> stringArray = new ArrayList<>();
        for (MenuElement menuElement : mainMenu.getSubElements()){
            if (menuElement instanceof JMenu){
                JMenu superMenu = (JMenu) menuElement;
                for(MenuElement subMenuElement : superMenu.getPopupMenu().getSubElements()){
                    JMenuItem subMenuItem = (JMenuItem) subMenuElement;
                    stringArray.add(superMenu.getText() + "::" + subMenuItem.getText());
                }
            }
            else if (menuElement instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) menuElement;
                stringArray.add(menuItem.getText());
            }
        }
        return stringArray;
    }
}
