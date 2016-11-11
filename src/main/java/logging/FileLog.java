package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class FileLog implements ServerObserver {

    private DefaultMessages defaultMessages;
    private Writer output;

    public FileLog(DefaultMessages defaultMessages) {
        this.defaultMessages = defaultMessages;
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String outputString = defaultMessages.serverHasBeenStartedMessage(new Date(), ipAddress, port) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String outputString = defaultMessages.serverHasBeenStoppedMessage(new Date(), ipAddress, port) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasConnected(String ipAddress) {
        String outputString = defaultMessages.clientHasConnectedMessage(new Date(), ipAddress) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasDisconnected(String ipAddress) {
        String outputString = defaultMessages.clientHasDisconnectedMessage(new Date(), ipAddress) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceRequested(String verb, String url) {
        String outputString = defaultMessages.resourceRequestedMessage(new Date(), verb, url) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceDelivered(String verb, String url, int statusCode) {
        String outputString = defaultMessages.resourceDeliveredMessage(new Date(), verb, url, statusCode) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
