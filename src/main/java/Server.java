import java.io.*;
import java.net.*;

public class Server {

    private int port;
    private ServerSocket serverSocket;

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

    public int port() {
        return port;
    }

    public ServerSocket serverSocket() {
        return serverSocket;
    }

    public void start() throws IOException {

        try {

            while(true) {

                Socket clientSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                String incomingRequest = bufferedReader.readLine();
                String[] split = incomingRequest.split("\\s+");

                if(split[0].equals("HEAD")) {
                    if (split[1].equals("/")) {
                        printWriter.println(successNoBodyResponse());
                    } else if (split[1].equals("/foo")) {
                        printWriter.println(successNoBodyResponse());
                    } else {
                        printWriter.println(notFoundResponse());
                    }
                } else {
                    if (split[1].equals("/")) {
                        printWriter.println(response("<h1>Hello World!</h1>"));
                    } else if (split[1].equals("/foo")) {
                        printWriter.println(response("foo"));
                    } else {
                        printWriter.println(notFoundResponse());
                    }
                }

                clientSocket.close();

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String response(String content) {
        HTTPHeader header = new HTTPHeader();
        return  header.success200StatusCode +
                header.contentType +
                header.contentLength("\n" + content) +
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

}