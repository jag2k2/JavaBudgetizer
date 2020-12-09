package flb.datastores;

import flb.util.WhichMonth;

public interface DefaultGoalStoreCreator extends StoreChangeNotifier {

    void createDefaultGoals(WhichMonth whichMonth);
    int countGoals(WhichMonth selectedMonth);
}
