package flb.components.monthselector;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import flb.util.*;

class SelectedMonthGetterImplTest {
    private ViewSelectorImpl monthSelector;

    @BeforeEach
    void setUp() {
        monthSelector = new ViewSelectorImpl();
    }

    @Test
    void getSelectedDate() {
        Calendar now = new GregorianCalendar();
        WhichMonth expected = new WhichMonth(now.get(Calendar.YEAR), now.get(Calendar.MONTH));

        WhichMonth actual = monthSelector.getSelectedMonth();

        assertEquals(expected, actual);
    }
}