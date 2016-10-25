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

                bufferedReader.readLine();
                printWriter.println(response("This is some response."));
                clientSocket.close();

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String response(String content) {
        HTTPHeader header = new HTTPHeader();
        return  header.statusCode +
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

}