package flb.components;

import flb.components.editor.ViewChangeObserver;

public interface ViewChangeNotifier {
    void addViewChangeObserver(ViewChangeObserver viewChangeObserver);
    void notifyViewChange();
}
