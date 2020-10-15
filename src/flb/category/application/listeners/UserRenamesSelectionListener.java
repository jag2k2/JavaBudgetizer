package flb.category.application.listeners;

import flb.category.application.*;
import java.beans.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryTableEditor tableEditor;

    public UserRenamesSelectionListener(CategoryTableEditor tableEditor){
        this.tableEditor = tableEditor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        tableEditor.userRenamesCategory(evt.getPropertyName());
    }
}
