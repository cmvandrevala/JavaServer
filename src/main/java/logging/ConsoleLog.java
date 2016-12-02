package logging;
import java.io.*;
import java.util.Date;

public class ConsoleLog implements ServerObserver {

    private LogMessages logMessages;
    private Writer output;

    public ConsoleLog(LogMessages logMessages) {
        this.logMessages = logMessages;
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String outputString = logMessages.serverHasBeenStartedMessage(new Date(), ipAddress, port);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String outputString = logMessages.serverHasBeenStoppedMessage(new Date(), ipAddress, port);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasConnected(String ipAddress) {
        String outputString = logMessages.clientHasConnectedMessage(new Date(), ipAddress);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasDisconnected(String ipAddress) {
        String outputString = logMessages.clientHasDisconnectedMessage(new Date(), ipAddress);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceRequested(String verb, String url) {
        String outputString = logMessages.resourceRequestedMessage(new Date(), verb, url);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceDelivered(String verb, String url, int statusCode) {
        String outputString = logMessages.resourceDeliveredMessage(new Date(), verb, url, statusCode);
        try {
            this.output = new BufferedWriter(new OutputStreamWriter(System.out));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
