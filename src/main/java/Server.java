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

    public static void main(String args[]) {}

    public void start() throws IOException {

        try {
            Socket clientSocket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            String str;

            while(true) {

                printWriter.println("Started server on port " + port + ".");
                printWriter.println("Client called " + clientSocket + ".");
                printWriter.println("Type 'bye' to exit the server.");

                 do {
                     printWriter.println("Enter your message: ");
                     str = bufferedReader.readLine();
                     printWriter.println("Received: " + str + ".");
                 } while( !str.equals("bye") );

                printWriter.println("Closing connection with client");
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