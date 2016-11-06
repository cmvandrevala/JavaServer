import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private int port;
    private ServerSocket serverSocket = new ServerSocket(port);
    private List<ServerObserver> observers = new ArrayList<ServerObserver>();

    public Server() throws IOException {
        this.port = 5000;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        while(true) {

            Socket clientSocket = serverSocket.accept();

            notifyServerStarted(clientSocket);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String incomingRequest = bufferedReader.readLine();

            notifyResourceRequested();

            HTTPResponse httpResponse = new HTTPResponse();

            try {
                String[] split = incomingRequest.split("\\s+");
                if (split[0].equals("HEAD")) {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(httpResponse.successNoBodyResponse());
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(httpResponse.successNoBodyResponse());
                    } else {
                        bufferedWriter.write(httpResponse.notFoundResponse());
                    }
                } else {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(httpResponse.response("<h1>Hello World!</h1>"));
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(httpResponse.response("foo"));
                    } else {
                        bufferedWriter.write(httpResponse.notFoundResponse());
                    }
                }
                notifyResponseDelivered();
            } catch (IOException e) {
                e.printStackTrace();
            }

            notifyServerStopped(clientSocket);
            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();

        }

    }

    public void tearDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    private void notifyServerStarted(Socket clientSocket) {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStarted(clientSocket.getRemoteSocketAddress().toString(), this.port);
        }
    }
    private void notifyServerStopped(Socket clientSocket) {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStopped(clientSocket.getRemoteSocketAddress().toString(), this.port);
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


}