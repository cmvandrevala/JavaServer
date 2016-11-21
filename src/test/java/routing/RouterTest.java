package routing;

import http_request.HTTPRequest;
import http_response.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private Router router;

    @Before
    public void setup() {
        RoutingTable routingTable = new RoutingTable();

        routingTable.addRoute("/", "GET");
        routingTable.addRoute("/", "HEAD");
        routingTable.addRoute("/foo", "GET");
        routingTable.addRoute("/foo", "HEAD");
        routingTable.addRoute("/method_options", "GET");
        routingTable.addRoute("/method_options", "HEAD");
        routingTable.addRoute("/method_options", "POST");
        routingTable.addRoute("/method_options", "PUT");
        routingTable.addRoute("/method_options2", "GET");

        this.router = new Router(routingTable);
    }

    @Test
    public void emptyRequestYields400StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPRequest request = new HTTPRequest(params);
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
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageOptionRequestYields404StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTION");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void indexGetRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooGetRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void indexHeadRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooHeadRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooDeleteRequestYieldsMethodNotAllowedMessage() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals("HTTP/1.1 405 Method Not Allowed" + FormattedStrings.newline + "Content-Type: text/html" + FormattedStrings.newline + "Content-Length: 0" + FormattedStrings.newline + "Connection: close" + FormattedStrings.newline + "",response.responseString());
    }

    @Test
    public void fooDeleteRequestYields405StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(405,response.statusCode());
    }

    @Test
    public void indexOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.newline + "Allow: OPTIONS,GET,HEAD" + FormattedStrings.newline + "Server: My Java Server" + FormattedStrings.newline + "Content-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void fooOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.newline + "Allow: OPTIONS,GET,HEAD" + FormattedStrings.newline + "Server: My Java Server" + FormattedStrings.newline + "Content-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.newline + "Allow: OPTIONS,GET,HEAD,POST,PUT" + FormattedStrings.newline + "Server: My Java Server" + FormattedStrings.newline + "Content-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptions2RequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options2");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.newline + "Allow: OPTIONS,GET" + FormattedStrings.newline + "Server: My Java Server" + FormattedStrings.newline + "Content-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void putReturnsAStatusCodeOf200() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        params.put("Content-Length", "0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void the411StatusCodeHasTheCorrectOutput() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 411 Length Required" + FormattedStrings.newline + "Content-Type: text/html" + FormattedStrings.newline + "Content-Length: 0" + FormattedStrings.newline + "Connection: close" + FormattedStrings.newline + "";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void postExists() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "POST");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

}
