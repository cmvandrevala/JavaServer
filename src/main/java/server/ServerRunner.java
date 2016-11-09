package server;

import logging.*;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        Server server = new Server();
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        server.start();

    }

}
