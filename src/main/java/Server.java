import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private List<ServerObserver> observers = new ArrayList<ServerObserver>();

    public Server() throws IOException {
        this.port = 5000;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port) throws IOException {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        while(true) {

            Socket clientSocket = serverSocket.accept();

            notifyServerStarted();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String incomingRequest = bufferedReader.readLine();

            notifyResourceRequested();

            try {
                String[] split = incomingRequest.split("\\s+");
                if (split[0].equals("HEAD")) {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(successNoBodyResponse());
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(successNoBodyResponse());
                    } else {
                        bufferedWriter.write(notFoundResponse());
                    }
                } else {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(response("<h1>Hello World!</h1>"));
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(response("foo"));
                    } else {
                        bufferedWriter.write(notFoundResponse());
                    }
                }
                notifyResponseDelivered();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();

            notifyServerStopped();
        }

    }

    private void notifyResourceRequested() {
        for(ServerObserver observer: observers) {
            observer.resourceRequested("VERB", "URL");
        }
    }

    private void notifyResponseDelivered() {
        for(ServerObserver observer: observers) {
            observer.responseDelivered("VERB", "URL", 200);
        }
    }

    private void notifyServerStarted() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStarted("127.0.0.1", this.port);
        }
    }

    public String response(String content) {
        HTTPHeader header = new HTTPHeader();
        return  header.success200StatusCode +
                header.contentType +
                header.contentLength(content) +
                header.connection +
                header.spaceBetweenHeaderAndContent +
                content;
    }

    public void tearDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String notFoundResponse() {
        HTTPHeader header = new HTTPHeader();
        return  header.notFound404StatusCode +
                header.contentType +
                header.contentLength("") +
                header.connection;
    }

    public String successNoBodyResponse() {
        HTTPHeader header = new HTTPHeader();
        return  header.success200StatusCode +
                header.contentType +
                header.contentLength("") +
                header.connection;
    }

    public void registerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    private void notifyServerStopped() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStopped("127.0.0.1", this.port);
        }
    }


}