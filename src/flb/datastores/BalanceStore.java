package flb.datastores;

import flb.util.WhichMonth;

public interface BalanceStore extends StoreChangeNotifier {
    float getBalance(WhichMonth whichMonth);
}
