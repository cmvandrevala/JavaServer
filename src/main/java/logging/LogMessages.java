package logging;

import java.util.Date;

public interface LogMessages {

    String serverHasBeenStartedMessage(Date date, String ipAddress, int port);
    String serverHasBeenStoppedMessage(Date date, String ipAddress, int port);
    String clientHasConnectedMessage(Date date, String ipAddress);
    String clientHasDisconnectedMessage(Date date, String ipAddress);
    String resourceRequestedMessage(Date date, String verb, String url);
    String resourceDeliveredMessage(Date date, String verb, String url, int statusCode);

}
