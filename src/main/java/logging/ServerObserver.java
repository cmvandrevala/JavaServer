package logging;

public interface ServerObserver {

    void serverHasBeenStarted(String ipAddress, int port);
    void clientHasConnected(String ipAddress);
    void clientHasDisconnected(String ipAddress);
    void resourceRequested(String verb, String url);
    void resourceDelivered(String verb, String url, int statusCode);

}
