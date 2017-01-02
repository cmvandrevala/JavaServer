package server;

import logging.ServerObserver;
import routing.DataTable;
import routing.PathToUrlMapper;
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

    private ServerSocket serverSocket = null;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
    private boolean isStopped = false;

    private final RoutesTable routesTable;
    private final DataTable dataTable;
    private final PathToUrlMapper mapper;
    private Thread runningThread = null;
    private final List<ServerObserver> observers = new ArrayList<>();

    public Runner(RoutesTable routesTable, DataTable dataTable, PathToUrlMapper mapper) throws IOException {
        this.routesTable = routesTable;
        this.dataTable = dataTable;
        this.mapper = mapper;
    }

    public void run() {

        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }

        startServer();
        notifyServerStarted();

        while(!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    notifyServerStopped();
                    break;
                }
                throw new RuntimeException("Error Accepting Client Connection", e);
            }
            this.threadPool.execute(new SocketHandler(clientSocket, this.routesTable, this.dataTable, this.mapper, this.observers));
        }

        shutdownThreadPool();

    }

    synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() { this.isStopped = true; }

    private void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
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