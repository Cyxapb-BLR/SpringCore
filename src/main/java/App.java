import beans.Client;
import beans.Event;
import loggers.ConsoleEventLogger;
import loggers.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    Client client;
    EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public App() {
    }

    public static void main(String[] args) {
       /* App app = new App();
        app.client = new Client("1", "John Smith");
        app.eventLogger = new ConsoleEventLogger();

        app.logEvent("Some event for user 1");*/

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) context.getBean("app");
//        App app = context.getBean(App.class);

        Event event = context.getBean(Event.class);
        app.logEvent(event, "Some event for user 1");

        event = context.getBean(Event.class);
        app.logEvent(event, "Some event for user 2");

    }

    private void logEvent(Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);
        eventLogger.logEvent(event);
    }
}
