package server;

import http_request.HTTPRequest;
import http_request.Router;
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
            String incomingRequest = "";

            for (String line; (line = bufferedReader.readLine()) != null;) {
                if (line.length() == 0) {
                    break;
                }
                if(incomingRequest.length() == 0) {
                    incomingRequest = line;
                } else {
                    incomingRequest = incomingRequest + "\r\n" + line;
                }
            }

            HTTPRequest request = new HTTPRequest(incomingRequest);
            Router router = new Router(bufferedWriter, observers);
            router.route(request);
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


}