package server;

import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.HTTPResponse;
import http_response.Response;
import http_response.ResponseWriter;
import logging.ServerObserver;
import routing.DataTable;
import routing.Router;
import routing.RoutesTable;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketHandler implements Runnable {

    private final Socket clientSocket;
    private final RoutesTable routesTable;
    private final DataTable dataTable;
    private List<ServerObserver> observers;

    SocketHandler(Socket socket, RoutesTable routesTable, DataTable dataTable, List<ServerObserver> observers) {
        this.clientSocket = socket;
        this.observers = observers;
        this.routesTable = routesTable;
        this.dataTable = dataTable;
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
        Router router = new Router(this.routesTable, this.dataTable);
        ResponseWriter writer = new ResponseWriter();
        Response response = router.route(request);
        bufferedWriter.write(writer.writeHttpResponse(response));
        notifyResourceDelivered(request.verb(), request.url(), Integer.parseInt(response.statusCode()));
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
