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

        while(true) {

            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String incomingRequest = bufferedReader.readLine();

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
            } catch (IOException e) {
                e.printStackTrace();
            }

            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();

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

}