package logging;

import java.util.Date;

class MockLogger implements LogMessages {

    public String serverHasBeenStartedMessage(Date date, String ipAddress, int port) {
        return "serverHasBeenStarted was called with " + ipAddress + " and " + port;
    }

    public String serverHasBeenStoppedMessage(Date date, String ipAddress, int port) {
        return "serverHasBeenStopped was called with " + ipAddress + " and " + port;
    }

    public String clientHasConnectedMessage(Date date, String ipAddress) {
        return "clientHasConnected was called with " + ipAddress;
    }

    public String clientHasDisconnectedMessage(Date date, String ipAddress) {
        return "clientHasDisconnected was called with " + ipAddress;
    }

    public String resourceRequestedMessage(Date date, String verb, String url) {
        return "resourceRequested was called with " + verb + " and " + url;
    }

    public String resourceDeliveredMessage(Date date, String verb, String url, int statusCode) {
        return "resourceDelivered was called with " + verb + ", " + url + ", and " + statusCode;
    }
}