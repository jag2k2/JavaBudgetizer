package com.jag2k2.datastores;

import com.jag2k2.util.WhichMonth;

public interface BalanceStore extends StoreChangeNotifier {
    float getBalance(WhichMonth whichMonth);
}
