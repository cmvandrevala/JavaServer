package server;

import logging.ServerObserver;
import routing.DataTable;
import routing.RoutesTable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner implements Runnable {

    int portNumber = 5000;
    int numberOfThreads = 10;

    private final RoutesTable routesTable;
    private final DataTable dataTable;
    private ServerSocket serverSocket = null;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
    private boolean isStopped = false;
    private final List<ServerObserver> observers = new ArrayList<>();

    public Runner(RoutesTable routesTable, DataTable dataTable) throws IOException {
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public void run() {

        startServer();

        while(!isStopped()) {
            try {
                processIncomingRequest();
            } catch (IOException e) {
                e.printStackTrace();
                if(isStopped()) { break; }
            }
        }

        shutdownThreadPool();

    }

    synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() { this.isStopped = true; }

    private void processIncomingRequest() throws IOException {
        Socket clientSocket = this.serverSocket.accept();
        this.threadPool.execute(new SocketHandler(clientSocket, this.routesTable, this.dataTable, this.observers));
    }

    private void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
            notifyServerStarted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdownThreadPool() {
        this.threadPool.shutdown();
        notifyServerStopped();
    }

    void registerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    private void notifyServerStarted() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStarted(this.serverSocket.getInetAddress().toString(), this.portNumber);
        }
    }

    private void notifyServerStopped() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStopped(this.serverSocket.getInetAddress().toString(), this.portNumber);
        }
    }

}