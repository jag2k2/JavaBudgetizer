package flb.components;

import static org.junit.jupiter.api.Assertions.*;

import flb.util.WhichMonth;
import org.junit.jupiter.api.*;

import java.util.*;

class MonthSelectorImpTest {
    private MonthSelectorImp monthSelector;

    @BeforeEach
    void setUp() {
        this.monthSelector = new MonthSelectorImp();
    }

    @Test
    void getSelectedDate() {
        Calendar now = new GregorianCalendar();
        WhichMonth expected = new WhichMonth(now.get(Calendar.YEAR), now.get(Calendar.MONTH));

        WhichMonth actual = monthSelector.getSelectedDate();

        assertEquals(expected, actual);
    }
}