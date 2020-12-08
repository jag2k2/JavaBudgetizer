package flb.components.monthselector;

import flb.components.ViewChangeNotifier;
import flb.util.WhichMonth;

public interface MonthDisplay extends ViewChangeNotifier {
    WhichMonth getMonth();
}
