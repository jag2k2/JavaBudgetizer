package flb.components;

import flb.components.editors.ViewChangeObserver;

public interface ViewChangeNotifier {
    void addViewChangeObserver(ViewChangeObserver viewChangeObserver);
    void notifyViewChange();
}
