package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AppTest {
    private static final String MSG = "Hello";

    @Test
    public void testClientNameSubstitution() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Client client = new Client("25", "Bob");
        DummyLogger dummyLogger = new DummyLogger();

        App app = new App(client, dummyLogger, Collections.emptyMap());
        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());

        invokeLogEvent(app, null, event, MSG + " " + client.getId());
        assertTrue(dummyLogger.getEvent().getMsg().contains(MSG));
        assertTrue(dummyLogger.getEvent().getMsg().contains(client.getFullName()));

        invokeLogEvent(app, null, event, MSG + "0");
        assertTrue(dummyLogger.getEvent().getMsg().contains(MSG));
        assertFalse(dummyLogger.getEvent().getMsg().contains(client.getFullName()));

    }

    @Test
    public void testCorrectLoggerCall() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Client client = new Client("25", "Bob");
        DummyLogger defaultLogger = new DummyLogger();
        DummyLogger infoLogger = new DummyLogger();

        @SuppressWarnings("serial")
        App app = new App(client, defaultLogger, new HashMap<EventType, EventLogger>() {{
            put(EventType.INFO, infoLogger);
        }});

        Event event = new Event(new Date(), DateFormat.getDateTimeInstance());

        invokeLogEvent(app, null, event, MSG + " " + client.getId());
        assertNotNull(defaultLogger.getEvent());
        assertNull(infoLogger.getEvent());

        defaultLogger.setEvent(null);
        infoLogger.setEvent(null);

        invokeLogEvent(app, EventType.ERROR, event, MSG + " " + client.getId());
        assertNotNull(defaultLogger.getEvent());
        assertNull(infoLogger.getEvent());

        defaultLogger.setEvent(null);
        infoLogger.setEvent(null);

        invokeLogEvent(app, EventType.INFO, event, MSG + " 0");
        assertNull(defaultLogger.getEvent());
        assertNotNull(infoLogger.getEvent());
    }

    private void invokeLogEvent(App app, EventType eventType, Event event, String message) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = app.getClass().getDeclaredMethod("logEvent", EventType.class, Event.class, String.class);
        method.setAccessible(true);
        method.invoke(app, eventType, event, message);
    }

    private class DummyLogger implements EventLogger {

        private Event event;

        public void setEvent(Event event) {
            this.event = event;
        }

        @Override
        public void logEvent(Event event) {
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }

    }
}
