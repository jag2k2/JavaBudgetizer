package com.jag2k2.components;

import com.jag2k2.components.editor.ViewChangeObserver;

public interface ViewChangeNotifier {
    void addViewChangeObserver(ViewChangeObserver viewChangeObserver);
    void notifyViewChange();
}
