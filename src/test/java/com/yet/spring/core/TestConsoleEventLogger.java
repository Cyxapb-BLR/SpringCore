package com.yet.spring.core;

import com.yet.spring.core.beans.Event;
import com.yet.spring.core.loggers.ConsoleEventLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

public class TestConsoleEventLogger {

    private static final String MSG = "Message";
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void cleanUpStream() {
        System.setOut(null);
    }

    @Test
    public void testLogEvent() {
        ConsoleEventLogger eventLogger = new ConsoleEventLogger();
        Date date = new Date();
        Event event = new Event(date, DateFormat.getDateTimeInstance());
        event.setMsg(MSG);

        eventLogger.logEvent(event);

        Assert.assertTrue(outputStream.toString().contains(MSG));

        Assert.assertEquals(event.toString().trim(), outputStream.toString().trim());
    }
}
