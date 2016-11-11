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
    }

    @Test
    public void emptyRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),404);
    }

    @Test
    public void missingPageGetRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),404);
    }

    @Test
    public void indexGetRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),200);
    }

    @Test
    public void fooGetRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),200);
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/missing");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),404);
    }

    @Test
    public void indexHeadRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/2.0");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),200);
    }

    @Test
    public void fooHeadRequestYields200StatusCode() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "HEAD");
        params.put("URL", "/foo");
        params.put("Protocol", "HTTP/1.1");
        HTTPRequest request = new HTTPRequest(params);
        HTTPResponse response = router.route(request);
        assertEquals(response.statusCode(),200);
    }

}
