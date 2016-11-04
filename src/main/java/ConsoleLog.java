import java.util.Date;

public class ConsoleLog implements ServerObserver {

    public Logger logger;

    public ConsoleLog() {
        logger = new Logger();
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String output = logger.serverHasBeenStartedMessage(new Date(), ipAddress, port);
        System.out.println(output);
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String output = logger.serverHasBeenStoppedMessage(new Date(), ipAddress, port);
        System.out.println(output);
    }

    public void resourceRequested(String verb, String url) {
        String output = logger.resourceRequestedMessage(new Date(), verb, url);
        System.out.println(output);
    }

    public void responseDelivered(String verb, String url, int statusCode) {
        String output = logger.resourceDeliveredMessage(new Date(), verb, url, statusCode);
        System.out.println(output);
    }

}
