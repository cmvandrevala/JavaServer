public class JavaServerRunner {

    public static void main(String args[]) throws Exception {

        Server server = new Server();
        server.registerObserver(new ConsoleLog());
        server.registerObserver(new FileLog());
        server.start();

    }

}
