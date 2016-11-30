package routing;

import http_request.Request;
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
    public void indexGetRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooGetRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
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
    public void indexHeadRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooHeadRequestYields200StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooDeleteRequestYieldsMethodNotAllowedMessage() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals("HTTP/1.1 405 Method Not Allowed" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "",response.responseString());
    }

    @Test
    public void fooDeleteRequestYields405StatusCode() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(405,response.statusCode());
    }

    @Test
    public void indexOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void fooOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD,POST,PUT" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptions2RequestYieldsTheCorrectStringResponse() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options2");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
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
    public void the411StatusCodeHasTheCorrectOutput() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 411 Length Required" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void postExists() throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "POST");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

}
