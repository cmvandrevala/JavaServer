package server;

import http_request.HTTPRequest;
import http_request.HTTPRequestParser;
import routing.Router;
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
            boolean streamIncoming = true;
            String requestBody = "";

            while(streamIncoming) {
                if(bufferedReader.ready()) {
                    input = bufferedReader.read();
                    if(input != -1) {
                        requestBody = requestBody + (char) input;
                    } else {
                        streamIncoming = false;
                    }
                } else {
                    streamIncoming = false;
                }
            }

            String incomingRequest = requestBody;

            HTTPRequestParser builder = new HTTPRequestParser();
            HTTPRequest request = builder.build(incomingRequest);

            notifyResourceRequested(request.verb(), request.url());

            HTTPResponse response = this.router.route(request);
            bufferedWriter.write(response.responseString());

            notifyResourceDelivered(request.verb(), request.url(), response.statusCode());

            notifyClientDisconnected(clientSocket);

            bufferedWriter.close();
            clientSocket.close();
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