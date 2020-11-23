package flb.datastores;

import flb.components.editor.StoreChangeObserver;

public interface StoreChangeNotifier {
    void addStoreChangeObserver(StoreChangeObserver storeChangeObserver);
    void notifyStoreChange();
}
