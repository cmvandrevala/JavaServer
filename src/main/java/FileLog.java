import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class FileLog implements ServerObserver {

    private Logger logger;
    private Writer output;
    private FileWriter fileWriter;

    public FileLog(Logger logger) {
        this.logger = logger;
    }

    public void serverHasBeenStarted(String ipAddress, int port) {
        String outputString = logger.serverHasBeenStartedMessage(new Date(), ipAddress, port) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHasBeenStopped(String ipAddress, int port) {
        String outputString = logger.serverHasBeenStoppedMessage(new Date(), ipAddress, port) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resourceRequested(String verb, String url) {
        String outputString = logger.resourceRequestedMessage(new Date(), verb, url) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseDelivered(String verb, String url, int statusCode) {
        String outputString = logger.resourceDeliveredMessage(new Date(), verb, url, statusCode) + "\r\n";
        try {
            this.output = new BufferedWriter(new FileWriter("server.log", true));
            this.output.append(outputString);
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
