package server;

import http_request.HTTPRequest;
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

    public Server() throws IOException {
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

            String incomingRequest = bufferedReader.readLine();

            HTTPRequest request = new HTTPRequest(incomingRequest);
            HTTPResponse response = new HTTPResponse();

            try {
                notifyResourceRequested(request.verb, request.url);
                if (request.verb.equals("HEAD")) {
                    if (request.url.equals("/")) {
                        bufferedWriter.write(response.successNoBodyResponse());
                        notifyResourceDelivered(request.verb, request.url, 200);
                    } else if (request.url.equals("/foo")) {
                        bufferedWriter.write(response.successNoBodyResponse());
                        notifyResourceDelivered(request.verb, request.url, 200);
                    } else {
                        bufferedWriter.write(response.notFoundResponse());
                        notifyResourceDelivered(request.verb, request.url, 404);
                    }
                } else {
                    if (request.url.equals("/")) {
                        bufferedWriter.write(response.response("<h1>Hello World!</h1>"));
                        notifyResourceDelivered(request.verb, request.url, 200);
                    } else if (request.url.equals("/foo")) {
                        bufferedWriter.write(response.response("foo"));
                        notifyResourceDelivered(request.verb, request.url, 200);
                    } else {
                        bufferedWriter.write(response.notFoundResponse());
                        notifyResourceDelivered(request.verb, request.url, 404);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    private void notifyResourceDelivered(String verb, String url, int ipAddress) {
        for(ServerObserver observer: observers) {
            observer.resourceDelivered(verb, url, ipAddress);
        }
    }


}