import junit.framework.TestCase;

public class ServerTest {
    @org.junit.Test
    public void echo() throws Exception {
        Server s = new Server();
        TestCase.assertEquals( s.echo("Hello World!"), "Hello World!" );
    }

}