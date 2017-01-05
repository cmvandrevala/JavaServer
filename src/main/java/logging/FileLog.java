package logging;

import utilities.FormattedStrings;

import java.io.*;
import java.util.Date;

public class FileLog implements ServerObserver {
    
    private LogMessages logMessages;
    private String filename;
    private Writer output;

    public FileLog(LogMessages logMessages, String filename) {
        this.logMessages = logMessages;
        this.filename = filename;
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String outputString = logMessages.serverHasBeenStartedMessage(new Date(), ipAddress, port) + FormattedStrings.CRLF;
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String outputString = logMessages.serverHasBeenStoppedMessage(new Date(), ipAddress, port) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasConnected(String ipAddress) {
        String outputString = logMessages.clientHasConnectedMessage(new Date(), ipAddress) + FormattedStrings.CRLF;
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientHasDisconnected(String ipAddress) {
        String outputString = logMessages.clientHasDisconnectedMessage(new Date(), ipAddress) + FormattedStrings.CRLF;
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceRequested(String verb, String url) {
        String outputString = logMessages.resourceRequestedMessage(new Date(), verb, url) + FormattedStrings.CRLF;
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceDelivered(String verb, String url, int statusCode) {
        String outputString = logMessages.resourceDeliveredMessage(new Date(), verb, url, statusCode) + FormattedStrings.CRLF;
        try {
            this.output = new BufferedWriter(new FileWriter(filename, true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
