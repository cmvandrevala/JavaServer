package server;

import logging.ServerObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService threadPool;
    private List<ServerObserver> observers = new ArrayList<>();

    public Server(int portNumber, int numberOfThreads) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
        this.threadPool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void start() throws IOException {
        notifyServerStarted();
        try {
            while(true) {
                threadPool.execute(new SocketHandler(serverSocket.accept(), observers));
            }
        } catch (IOException ex) {
            threadPool.shutdown();
        }
    }

    void registerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    private void notifyServerStarted() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStarted(this.serverSocket.getInetAddress().toString(), 5000);
        }
    }

}