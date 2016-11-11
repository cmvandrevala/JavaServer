package logging;
import java.util.Date;

public class ConsoleLog implements ServerObserver {

    private LogMessages logMessages;

    public ConsoleLog(LogMessages logMessages) {
        this.logMessages = logMessages;
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String output = logMessages.serverHasBeenStartedMessage(new Date(), ipAddress, port);
        System.out.println(output);
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String output = logMessages.serverHasBeenStoppedMessage(new Date(), ipAddress, port);
        System.out.println(output);
    }

    public void clientHasConnected(String ipAddress) {
        String output = logMessages.clientHasConnectedMessage(new Date(), ipAddress);
        System.out.println(output);
    }

    public void clientHasDisconnected(String ipAddress) {
        String output = logMessages.clientHasDisconnectedMessage(new Date(), ipAddress);
        System.out.println(output);
    }

    public void resourceRequested(String verb, String url) {
        String output = logMessages.resourceRequestedMessage(new Date(), verb, url);
        System.out.println(output);
    }

    public void resourceDelivered(String verb, String url, int statusCode) {
        String output = logMessages.resourceDeliveredMessage(new Date(), verb, url, statusCode);
        System.out.println(output);
    }

}
