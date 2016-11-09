package server;

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

            HTTPResponse httpResponse = new HTTPResponse();

            try {
                String[] split = incomingRequest.split("\\s+");
                notifyResourceRequested(split[0], split[1]);
                if (split[0].equals("HEAD")) {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(httpResponse.successNoBodyResponse());
                        notifyResourceDelivered(split[0], split[1], 200);
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(httpResponse.successNoBodyResponse());
                        notifyResourceDelivered(split[0], split[1], 200);
                    } else {
                        bufferedWriter.write(httpResponse.notFoundResponse());
                        notifyResourceDelivered(split[0], split[1], 404);
                    }
                } else {
                    if (split[1].equals("/")) {
                        bufferedWriter.write(httpResponse.response("<h1>Hello World!</h1>"));
                        notifyResourceDelivered(split[0], split[1], 200);
                    } else if (split[1].equals("/foo")) {
                        bufferedWriter.write(httpResponse.response("foo"));
                        notifyResourceDelivered(split[0], split[1], 200);
                    } else {
                        bufferedWriter.write(httpResponse.notFoundResponse());
                        notifyResourceDelivered(split[0], split[1], 404);
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