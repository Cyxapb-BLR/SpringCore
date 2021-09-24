import beans.Event;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

public class TestEvent {

    @Test
    public void testToString() {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance();

        Event event = new Event(date, dateFormat);

        String str = event.toString();

        Assert.assertTrue(str.contains(dateFormat.format(date)));
    }

    @Test
    public void testToString2() {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Event event = new Event(date, dateFormat);

        String str = event.toString();

        Assert.assertTrue(str.contains(dateFormat.format(date)));
    }
}
