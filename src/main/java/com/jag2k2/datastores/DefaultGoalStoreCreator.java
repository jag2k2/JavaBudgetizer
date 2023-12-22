package com.jag2k2.datastores;

import com.jag2k2.util.WhichMonth;

public interface DefaultGoalStoreCreator extends StoreChangeNotifier {

    void createDefaultGoals(WhichMonth whichMonth);
    int countGoals(WhichMonth selectedMonth);
}
