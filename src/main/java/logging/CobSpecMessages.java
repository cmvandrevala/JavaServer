package logging;

import java.util.Date;

public class CobSpecMessages implements LogMessages {

    public String serverHasBeenStartedMessage(Date date, String ipAddress, int port) {
        return "Server Started";
    }

    public String serverHasBeenStoppedMessage(Date date, String ipAddress, int port) {
        return "Server Stopped";
    }

    public String clientHasConnectedMessage(Date date, String ipAddress) {
        return "Client Connected";
    }

    public String clientHasDisconnectedMessage(Date date, String ipAddress) {
        return "Client Disconnected";
    }

    public String resourceRequestedMessage(Date date, String verb, String url) {
        return "Request: " + verb + " " + url + " HTTP/1.1";
    }

    public String resourceDeliveredMessage(Date date, String verb, String url, int statusCode) {
        return "Response: " + verb + " " + url + " HTTP/1.1";
    }
}
