package server;

import logging.ServerObserver;
import routing.RoutesTable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private RoutesTable routesTable;
    private int portNumber = 5000;
    private ServerSocket serverSocket = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private Thread runningThread = null;
    private boolean isStopped = false;
    private List<ServerObserver> observers = new ArrayList<>();

    public Server(int portNumber, RoutesTable routesTable) throws IOException {
        this.portNumber = portNumber;
        this.routesTable = routesTable;
    }

    public void run(){

        synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();
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
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            this.threadPool.execute(new SocketHandler(clientSocket, routesTable, observers));
        }
        this.threadPool.shutdown();
        notifyServerStopped();
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void notifyServerStopped() {
        for(ServerObserver observer: observers) {
            observer.serverHasBeenStopped(this.serverSocket.getInetAddress().toString(), 5000);
        }
    }

}