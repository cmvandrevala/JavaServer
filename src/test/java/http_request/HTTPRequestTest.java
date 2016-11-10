package http_request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestTest {

    private HTTPRequest emptyRequest;
    private HTTPRequest shortRequest;
    private HTTPRequest tutsPlusRequest;

    private String shortGetRequest = "GET / HTTP/1.1\n" +
                                "Host: localhost:5000\n" +
                                "User-Agent: curl/7.50.3\n" +
                                "Accept: */*";

    private String tutsPlusGetRequest = "GET /tutorials/other/top-20-mysql-best-practices/ HTTP/2.0\n" +
                                        "Host: net.tutsplus.com\n" +
                                        "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)\n" +
                                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                                        "Accept-Language: en-us,en;q=0.5\n" +
                                        "Accept-Encoding: gzip,deflate\n" +
                                        "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
                                        "Keep-Alive: 300\n" +
                                        "Connection: keep-alive\n" +
                                        "Cookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120\n" +
                                        "Pragma: no-cache\n" +
                                        "Cache-Control: no-cache";

    @Before
    public void setup() {
        emptyRequest = new HTTPRequest("");
        shortRequest = new HTTPRequest(shortGetRequest);
        tutsPlusRequest = new HTTPRequest(tutsPlusGetRequest);
    }

    @Test
    public void verbIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.verb());
    }

    @Test
    public void urlIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.url());
    }

    @Test
    public void protocolIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.protocol());
    }

    @Test
    public void hostIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.host());
    }

    @Test
    public void userAgentIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.userAgent());
    }

    @Test
    public void acceptIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.accept());
    }

    @Test
    public void acceptLanguageIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.acceptLanguage());
    }

    @Test
    public void acceptEncodingIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.acceptEncoding());
    }

    @Test
    public void acceptCharsetIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.acceptCharset());
    }

    @Test
    public void keepAliveIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.keepAlive());
    }

    @Test
    public void connectionIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.connection());
    }

    @Test
    public void cookieIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.cookie());
    }

    @Test
    public void pragmaIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.pragma());
    }

    @Test
    public void cacheControlIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.cacheControl());
    }

    @Test
    public void verbIsGetForShortGetRequests() {
        assertEquals("GET", shortRequest.verb());
    }

    @Test
    public void urlIsGivenForShortGetRequests() {
        assertEquals("/", shortRequest.url());
    }

    @Test
    public void protocolIsGivenForShortGetRequest() {
        assertEquals("HTTP/1.1", shortRequest.protocol());
    }

    @Test
    public void hostIsGivenForShortGetRequest() {
        assertEquals("localhost:5000", shortRequest.host());
    }

    @Test
    public void userAgentIsGivenForShortGetRequest() {
        assertEquals("curl/7.50.3", shortRequest.userAgent());
    }

    @Test
    public void acceptIsGivenForShortGetRequest() {
        assertEquals("*/*", shortRequest.accept());
    }

    @Test
    public void acceptLanguageIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.acceptLanguage());
    }

    @Test
    public void acceptEncodingIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.acceptEncoding());
    }

    @Test
    public void acceptCharsetIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.acceptCharset());
    }

    @Test
    public void keepAliveIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.keepAlive());
    }

    @Test
    public void connectionIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.connection());
    }

    @Test
    public void cookieIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.cookie());
    }

    @Test
    public void pragmaIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.pragma());
    }

    @Test
    public void cacheControlIsEmptyForShortGetRequest() {
        assertEquals("", shortRequest.cacheControl());
    }

    @Test
    public void verbIsGetForTutsPlusRequest() {
        assertEquals("GET", tutsPlusRequest.verb());
    }

    @Test
    public void urlIsGivenForTutsPlusRequest() {
        assertEquals("/tutorials/other/top-20-mysql-best-practices/", tutsPlusRequest.url());
    }

    @Test
    public void protocolIsGivenForTutsPlusRequest() {
        assertEquals("HTTP/2.0", tutsPlusRequest.protocol());
    }

    @Test
    public void hostIsGivenForTutsPlusRequest() {
        assertEquals("net.tutsplus.com", tutsPlusRequest.host());
    }

    @Test
    public void userAgentIsGivenForTutsPlusRequest() {
        assertEquals("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)", tutsPlusRequest.userAgent());
    }

    @Test
    public void acceptIsGivenForTutsPlusRequest() {
        assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", tutsPlusRequest.accept());
    }

    @Test
    public void acceptLanguageGivenForTutsPlusRequest() {
        assertEquals("en-us,en;q=0.5", tutsPlusRequest.acceptLanguage());
    }

    @Test
    public void acceptEncodingGivenForTutsPlusRequest() {
        assertEquals("gzip,deflate", tutsPlusRequest.acceptEncoding());
    }

    @Test
    public void acceptCharsetGivenForTutsPlusRequest() {
        assertEquals("ISO-8859-1,utf-8;q=0.7,*;q=0.7", tutsPlusRequest.acceptCharset());
    }

    @Test
    public void keepAliveGivenForTutsPlusRequest() {
        assertEquals("300", tutsPlusRequest.keepAlive());
    }

    @Test
    public void connectionGivenForTutsPlusRequest() {
        assertEquals("keep-alive", tutsPlusRequest.connection());
    }

    @Test
    public void cookieGivenForTutsPlusRequest() {
        assertEquals("PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120", tutsPlusRequest.cookie());
    }

    @Test
    public void pragmaGivenForTutsPlusRequest() {
        assertEquals("no-cache", tutsPlusRequest.pragma());
    }

    @Test
    public void cacheControlGivenForTutsPlusRequest() {
        assertEquals("no-cache", tutsPlusRequest.cacheControl());
    }

}
