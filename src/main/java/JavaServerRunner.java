public class JavaServerRunner {

    public static void main(String args[]) throws Exception {

        Server server = new Server();
        Logger logger = new Logger();
        server.registerObserver(new ConsoleLog(logger));
        server.registerObserver(new FileLog(logger));
        server.start();

    }

}
