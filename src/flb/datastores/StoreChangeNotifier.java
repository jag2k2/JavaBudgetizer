package flb.datastores;

import flb.components.editors.StoreChangeObserver;

public interface StoreChangeNotifier {
    void addStoreChangeObserver(StoreChangeObserver storeChangeObserver);
    void notifyStoreChange();
}
