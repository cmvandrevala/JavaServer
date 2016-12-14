package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GetResponseTest {

    private GetResponse response;
    private RequestBuilder builder;

    @Before
    public void setup() {
        this.builder = new RequestBuilder();
        Request request = this.builder.addUrl("/").build();
        this.response = new GetResponse(request, new RoutesTable(), new DataTable());
    }

    @Test
    public void getResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 21" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "<h1>Hello World!</h1>";
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        assertEquals(200, response.statusCode());
    }

    @Test
    public void plainTextResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/foo.txt").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("text/plain"));
    }

    @Test
    public void jpegResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/bar.jpeg").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("image/jpeg"));
    }

    @Test
    public void jpgResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/baz.jpg").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("image/jpeg"));
    }

    @Test
    public void pngResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/dir/baz.png").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("image/png"));
    }

    @Test
    public void gifResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/anotherDir/baz.gif").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("image/gif"));
    }

    @Test
    public void htmlResponse() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/foo.html").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("text/html"));
    }

    @Test
    public void htmlResponseWithNoExtension() throws Exception {
        Request request = this.builder.addUrl("/").addUrl("/foo").build();
        GetResponse plainTextResponse = new GetResponse(request, new RoutesTable(), new DataTable());
        assertTrue(plainTextResponse.responseString().contains("text/html"));
    }

}
