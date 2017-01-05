package logging;

import java.util.Date;

public class CobSpecMessages implements LogMessages {

    public String serverHasBeenStartedMessage(Date date, String ipAddress, int port) {
        return "<p>Server Started</p>";
    }

    public String serverHasBeenStoppedMessage(Date date, String ipAddress, int port) {
        return "<p>Server Stopped</p>";
    }

    public String clientHasConnectedMessage(Date date, String ipAddress) {
        return "<p>Client Connected</p>";
    }

    public String clientHasDisconnectedMessage(Date date, String ipAddress) {
        return "<p>Client Disconnected</p>";
    }

    public String resourceRequestedMessage(Date date, String verb, String url) {
        return "<p>Request: " + verb + " " + url + " HTTP/1.1</p>";
    }

    public String resourceDeliveredMessage(Date date, String verb, String url, int statusCode) {
        return "<p>Response: " + verb + " " + url + " HTTP/1.1</p>";
    }
}
