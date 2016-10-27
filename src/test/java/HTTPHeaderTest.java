import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HTTPHeaderTest {

    @Test
    public void statusCodeForSuccessfulGET() {
        HTTPHeader header = new HTTPHeader();
        assertEquals("HTTP/1.1 200 OK\n", header.success200StatusCode);
    }

    @Test
    public void statusCodeForNotFound() {
        HTTPHeader header = new HTTPHeader();
        assertEquals("HTTP/1.1 404 Not Found\n", header.notFound404StatusCode);
    }

    @Test
    public void contentTypeForSuccessfulGET() {
        HTTPHeader header = new HTTPHeader();
        assertEquals("Content-Type: text/html\n", header.contentType);
    }

    @Test
    public void contentLengthForSuccessfulGETWithNotContent() throws Exception {
        HTTPHeader header = new HTTPHeader();
        assertEquals("Content-Length: 0\n", header.contentLength(""));
    }

    @Test
    public void contentLengthForSuccessfulGETWithMinimalContent() throws Exception {
        HTTPHeader header = new HTTPHeader();
        String s = "Tiny String";
        assertEquals("Content-Length: 11\n", header.contentLength(s));
    }

    @Test
    public void contentLengthForSuccessfulGETWithHTMLFormattedContent() throws Exception {
        HTTPHeader header = new HTTPHeader();
        String s = "<html><head>This is a pretty sizable String</head><body><p>that also includes</p><p>Some HTML tags.</p></body></html>";
        assertEquals("Content-Length: 117\n", header.contentLength(s));
    }

    @Test
    public void connectionIsClosedAfterSuccessfulGET() throws Exception {
        HTTPHeader header = new HTTPHeader();
        assertEquals("Connection: close\n", header.connection);
    }

    @Test
    public void spacingBetweenHeaderAndContent() throws Exception {
        HTTPHeader header = new HTTPHeader();
        assertEquals("\n", header.spaceBetweenHeaderAndContent);
    }
}