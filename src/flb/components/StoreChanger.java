package flb.components;

import flb.components.editors.StoreChangeObserver;

public interface StoreChanger {
    void addStoreChangeObserver(StoreChangeObserver storeChangeObserver);
    void notifyStoreChange();
}
