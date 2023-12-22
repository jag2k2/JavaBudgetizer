package com.jag2k2.components.monthselector;

import com.jag2k2.components.ViewChangeNotifier;
import com.jag2k2.util.WhichMonth;

public interface MonthDisplay extends ViewChangeNotifier {
    WhichMonth getMonth();
}
