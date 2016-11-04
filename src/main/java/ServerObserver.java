public interface ServerObserver {

    void serverHasBeenStarted(String ipAddress, int port);
    void serverHasBeenStopped(String ipAddress, int port);
    void resourceRequested(String verb, String url);
    void responseDelivered(String verb, String url, int statusCode);

}
