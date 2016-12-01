package routing;

import http_request.Request;
import http_response.HTTPResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private Router router;
    private RoutingTable routingTable = RoutingTable.getInstance();

    @Before
    public void setup() {
        routingTable.addRoute("/method_options", "GET");
        routingTable.addRoute("/method_options", "HEAD");
        routingTable.addRoute("/method_options", "POST");
        routingTable.addRoute("/method_options", "PUT");
        routingTable.addRoute("/method_options", "DELETE");

        this.router = new Router();
    }

    @After
    public void teardown() {
        routingTable.clearData();
    }

    @Test
    public void emptyRequestYields400StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        Request request = new Request(params);
        request.setAsBadRequest();
        HTTPResponse response = router.route(request);
        assertEquals(400,response.statusCode());
    }

    @Test
    public void missingPageGetRequestYields404StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageOptionRequestYields404StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTION");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD,POST,PUT,DELETE" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void getReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void headReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "POST");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        params.put("Content-Length", "0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        params.put("Content-Length", "0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "POST");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

}
