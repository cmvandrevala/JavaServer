import java.io.*;
import java.net.*;

public class Server {

    public static void main(String args[]) throws Exception {}

    public void start() throws IOException {
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);

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

}