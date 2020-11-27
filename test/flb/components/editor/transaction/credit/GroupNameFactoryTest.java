package flb.components.editor.transaction.credit;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class GroupNameFactoryTest {

    @Test
    void testGroupNameCreator(){
        Calendar date = new GregorianCalendar(2020, Calendar.MAY, 1, 2, 3);
        float sum = -42F;
        String expected = "2020-05-01-0203:-42.00";

        String actual = GroupNameFactory.createGroupName(date, sum);
        assertEquals(expected, actual);

        date = new GregorianCalendar(2020, Calendar.MAY, 1, 14, 3);
        expected = "2020-05-01-1403:-42.00";

        actual = GroupNameFactory.createGroupName(date, sum);
        assertEquals(expected, actual);
    }
}