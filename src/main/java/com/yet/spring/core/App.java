package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
    }

    public App() {
    }

    public static void main(String[] args) {
       /* com.yet.spring.core.App app = new com.yet.spring.core.App();
        app.client = new Client("1", "John Smith");
        app.eventLogger = new ConsoleEventLogger();

        app.logEvent("Some event for user 1");*/

        //ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) context.getBean("app");
//        com.yet.spring.core.App app = context.getBean(com.yet.spring.core.App.class);

        Event event = context.getBean(Event.class);
        app.logEvent(EventType.INFO, event, "Some event for 1");

        event = context.getBean(Event.class);
        app.logEvent(EventType.ERROR, event, "Some event for 2");

        event = context.getBean(Event.class);
        app.logEvent(null, event, "Some event for 3");

        context.close();

    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);

        if (logger == null) {
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }
}
