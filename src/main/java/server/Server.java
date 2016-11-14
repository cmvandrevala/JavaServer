package server;

import http_request.HTTPRequest;
import http_request.HTTPRequestBuilder;
import http_request.Router;
import http_response.HTTPResponse;
import logging.ServerObserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private int port;
    private ServerSocket serverSocket = new ServerSocket(port);
    private List<ServerObserver> observers = new ArrayList<ServerObserver>();
    private Router router;

    public Server(Router router) throws IOException {
        this.router = router;
        this.port = 5000;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        notifyServerStarted();

        while(true) {

            Socket clientSocket = serverSocket.accept();

            notifyClientConnected(clientSocket);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            int input;
            StringBuilder requestBody = new StringBuilder();
            InputStream stream = clientSocket.getInputStream();

            while ((input = stream.read()) != -1 && stream.available() > 0) {
                requestBody.append((char) input);
            }

            String incomingRequest = requestBody.toString();

            HTTPRequestBuilder builder = new HTTPRequestBuilder();
            HTTPRequest request = new HTTPRequest(builder.tokenizeRequest(incomingRequest));

            notifyResourceRequested(request.verb(), request.url());

            HTTPResponse response = this.router.route(request);
            bufferedWriter.write(response.responseString());

            notifyResourceDelivered(request.verb(), request.url(), response.statusCode());

            notifyClientDisconnected(clientSocket);

            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();
        }

    }

    public void tearDown() {
        try {
            notifyServerStopped();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void registerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    private void notifyServerStarted() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStarted(this.serverSocket.getInetAddress().toString(), this.port);
        }
    }

    private void notifyServerStopped() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStopped(this.serverSocket.getInetAddress().toString(), this.port);
        }
    }

    private void notifyClientConnected(Socket clientSocket) {
        for(ServerObserver observer: observers) {
            observer.clientHasConnected(clientSocket.getRemoteSocketAddress().toString());
        }
    }

    private void notifyClientDisconnected(Socket clientSocket) {
        for(ServerObserver observer: observers) {
            observer.clientHasDisconnected(clientSocket.getRemoteSocketAddress().toString());
        }
    }

    private void notifyResourceRequested(String verb, String url) {
        for(ServerObserver observer: observers) {
            observer.resourceRequested(verb, url);
        }
    }

    private void notifyResourceDelivered(String verb, String url, int statusCode) {
        for(ServerObserver observer: observers) {
            observer.resourceDelivered(verb, url, statusCode);
        }
    }


}