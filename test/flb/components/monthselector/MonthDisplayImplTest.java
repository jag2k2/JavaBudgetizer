package flb.components.monthselector;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import flb.util.*;

class MonthDisplayImplTest {
    private MonthSelectorImpl monthSelector;

    @BeforeEach
    void setUp() {
        monthSelector = new MonthSelectorImpl();
    }

    @Test
    void getSelectedDate() {
        Calendar now = new GregorianCalendar();
        WhichMonth expected = new WhichMonth(now.get(Calendar.YEAR), now.get(Calendar.MONTH));

        WhichMonth actual = monthSelector.getMonth();

        assertEquals(expected, actual);
    }
}