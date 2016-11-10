package http_request;

import http_response.HTTPResponse;
import logging.ServerObserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class Router {

    private BufferedWriter bufferedWriter;
    private List<ServerObserver> observers;

    public Router(BufferedWriter bufferedWriter, List<ServerObserver> observers) {
        this.bufferedWriter = bufferedWriter;
        this.observers = observers;
    }

    public void route(HTTPRequest request) {

        HTTPResponse response = new HTTPResponse();
        notifyResourceRequested(request.verb(), request.url());

        try {
            if (request.verb().equals("HEAD")) {
                if (request.url().equals("/")) {
                    this.bufferedWriter.write(response.successNoBodyResponse());
                    notifyResourceDelivered(request.verb(), request.url(), 200);
                } else if (request.url().equals("/foo")) {
                    this.bufferedWriter.write(response.successNoBodyResponse());
                    notifyResourceDelivered(request.verb(), request.url(), 200);
                } else {
                    this.bufferedWriter.write(response.notFoundResponse());
                    notifyResourceDelivered(request.verb(), request.url(), 404);
                }
            } else {
                if (request.url().equals("/")) {
                    this.bufferedWriter.write(response.response("<h1>Hello World!</h1>"));
                    notifyResourceDelivered(request.verb(), request.url(), 200);
                } else if (request.url().equals("/foo")) {
                    this.bufferedWriter.write(response.response("foo"));
                    notifyResourceDelivered(request.verb(), request.url(), 200);
                } else {
                    this.bufferedWriter.write(response.notFoundResponse());
                    notifyResourceDelivered(request.verb(), request.url(), 404);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
