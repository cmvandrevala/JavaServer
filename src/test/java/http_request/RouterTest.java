package http_request;

import http_response.HTTPResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private Router router;

    @Before
    public void setup() {
        router = new Router();
        router.addRoute("/", "GET");
        router.addRoute("/", "HEAD");
        router.addRoute("/foo", "GET");
        router.addRoute("/foo", "HEAD");
        router.addRoute("/method_options", "GET");
        router.addRoute("/method_options", "HEAD");
        router.addRoute("/method_options", "POST");
        router.addRoute("/method_options", "PUT");
        router.addRoute("/method_options2", "GET");
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

    @Test
    public void filesAreStoredInARootDirectory() {
        assertEquals("cob_spec/public", router.rootDirectory);
    }

    @Test
    public void rootDirectoryCanBeUpdated() {
        router.rootDirectory = "new root";
        assertEquals("new root", router.rootDirectory);
    }

}
