package logging;

import java.util.Date;

public class DefaultMessages implements LogMessages {

    public String serverHasBeenStartedMessage(Date date, String ipAddress, int port) {
        return "[ " + date + " ] The server has been started on " + ipAddress + ":" + port;
    }

    public String clientHasConnectedMessage(Date date, String ipAddress) {
        return "[ " + date + " ] The client has connected on " + ipAddress;
    }

    public String clientHasDisconnectedMessage(Date date, String ipAddress) {
        return "[ " + date + " ] The client at " + ipAddress + " has disconnected";
    }

    public String resourceRequestedMessage(Date date, String verb, String url) {
        return "[ " + date + " ] The client sent a " + verb + " request to " + url;
    }

    public String resourceDeliveredMessage(Date date, String verb, String url, int statusCode) {
        return "[ " + date + " ] The server replied to the " + verb + " request to " + url + " with a status code of " + statusCode;
    }
}
