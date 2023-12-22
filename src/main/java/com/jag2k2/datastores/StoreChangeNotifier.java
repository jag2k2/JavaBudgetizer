package com.jag2k2.datastores;

import com.jag2k2.components.editor.StoreChangeObserver;

public interface StoreChangeNotifier {
    void addStoreChangeObserver(StoreChangeObserver storeChangeObserver);
    void notifyStoreChange();
}
