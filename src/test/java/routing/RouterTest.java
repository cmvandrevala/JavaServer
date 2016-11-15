package routing;

import http_request.HTTPRequest;
import http_response.HTTPResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private Router router;

    @Before
    public void setup() {
        RoutingTable routingTable = new RoutingTable();
        router = new Router(routingTable);
        routingTable.addRoute("/", "GET");
        routingTable.addRoute("/", "HEAD");
        routingTable.addRoute("/foo", "GET");
        routingTable.addRoute("/foo", "HEAD");
        routingTable.addRoute("/method_options", "GET");
        routingTable.addRoute("/method_options", "HEAD");
        routingTable.addRoute("/method_options", "POST");
        routingTable.addRoute("/method_options", "PUT");
        routingTable.addRoute("/method_options2", "GET");
    }

    @Test
    public void emptyRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageGetRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageOptionRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTION");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void indexGetRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooGetRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void indexHeadRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooHeadRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200,response.statusCode());
    }

    @Test
    public void fooDeleteRequestYieldsMethodNotAllowedMessage() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals("HTTP/1.1 405 Method Not Allowed\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n",response.responseString());
    }

    @Test
    public void fooDeleteRequestYields405StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "DELETE");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(405,response.statusCode());
    }

    @Test
    public void indexOptionsRequestYieldsTheCorrectStringResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK\r\nAllow: OPTIONS,GET,HEAD\r\nServer: My Java Server\r\nContent-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void fooOptionsRequestYieldsTheCorrectStringResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK\r\nAllow: OPTIONS,GET,HEAD\r\nServer: My Java Server\r\nContent-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectStringResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK\r\nAllow: OPTIONS,GET,HEAD,POST,PUT\r\nServer: My Java Server\r\nContent-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void methodOptions2RequestYieldsTheCorrectStringResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "OPTIONS");
        params.put("URL", "/method_options2");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK\r\nAllow: OPTIONS,GET\r\nServer: My Java Server\r\nContent-Length: 0";
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void putExists() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void postExists() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "POST");
        params.put("URL", "/method_options");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

}
