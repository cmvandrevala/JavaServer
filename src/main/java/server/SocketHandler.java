package server;

import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.HTTPResponse;
import logging.ServerObserver;
import routing.Router;
import routing.RoutingTable;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketHandler implements Runnable {

    private final Socket clientSocket;
    private List<ServerObserver> observers;
    private RoutingTable table = RoutingTable.getInstance();

    SocketHandler(Socket socket, List<ServerObserver> observers) {
        this.clientSocket = socket;
        this.observers = observers;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            notifyClientConnected(clientSocket);

            Request request = createRequestObject(bufferedReader);
            routeRequest(request, bufferedWriter);

            notifyClientDisconnected(clientSocket);
            bufferedWriter.close();
            bufferedReader.close();
            clientSocket.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Request createRequestObject(BufferedReader bufferedReader) throws IOException {
        RequestParser parser = new RequestParser();
        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);
        Request request = parser.parse(incomingRequest);
        notifyResourceRequested(request.verb(), request.url());
        return request;
    }

    private void routeRequest(Request request, BufferedWriter bufferedWriter) throws IOException {
        Router router = new Router(table);
        HTTPResponse response = router.route(request);
        bufferedWriter.write(response.responseString());
        notifyResourceDelivered(request.verb(), request.url(), response.statusCode());
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
