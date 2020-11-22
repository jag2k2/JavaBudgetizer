package flb.datastores;

import flb.components.editors.StoreChangeObserver;
import java.util.ArrayList;

abstract public class AbstractDataStore implements StoreChangeNotifier {
    private final ArrayList<StoreChangeObserver> storeChangeObservers;

    public AbstractDataStore(){
        this.storeChangeObservers = new ArrayList<>();
    }

    @Override
    public void notifyStoreChange() {
        for (StoreChangeObserver storeChangeObserver : storeChangeObservers) {
            storeChangeObserver.updateAndKeepSelection();
        }
    }

    @Override
    public void addStoreChangeObserver(StoreChangeObserver storeChangeObserver){
        storeChangeObservers.add(storeChangeObserver);
    }
}
