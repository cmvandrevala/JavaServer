package http_request;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    private Request emptyRequest;
    private Request shortRequest;
    private Request tutsPlusRequest;

    private Hashtable<String,String> emptyInput = new Hashtable<>();
    private Hashtable<String,String> shortInput = new Hashtable<>();
    private Hashtable<String,String> tutsPlusInput = new Hashtable<>();

    @Before
    public void setup() {
        emptyRequest = new Request(emptyInput);

        shortInput.put("Verb", "GET");
        shortInput.put("URL", "/");
        shortInput.put("Protocol", "HTTP/1.1");
        shortInput.put("Host", "localhost:5000");
        shortInput.put("User-Agent", "curl/7.50.3");
        shortInput.put("Accept", "*/*");
        shortRequest = new Request(shortInput);

        tutsPlusInput.put("Verb", "GET");
        tutsPlusInput.put("URL", "/tutorials/other/top-20-mysql-best-practices/");
        tutsPlusInput.put("Protocol", "HTTP/2.0");
        tutsPlusInput.put("Host", "net.tutsplus.com");
        tutsPlusInput.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)");
        tutsPlusInput.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        tutsPlusInput.put("Accept-Language", "en-us,en;q=0.5");
        tutsPlusInput.put("Accept-Encoding", "gzip,deflate");
        tutsPlusInput.put("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        tutsPlusInput.put("Keep-Alive", "300");
        tutsPlusInput.put("Connection", "keep-alive");
        tutsPlusInput.put("Cookie", "PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120");
        tutsPlusInput.put("Pragma", "no-cache");
        tutsPlusInput.put("Cache-Control", "no-cache");
        tutsPlusInput.put("Query-Params-String", "var1=this%20is%20some%20string");
        tutsPlusRequest = new Request(tutsPlusInput);
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
    public void emptyRequestHasNoBody() { assertEquals("", emptyRequest.body()); }

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
    public void queryParamsStringEmptyForShortGetRequest() {
        assertEquals("", shortRequest.queryParamsString());
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

    @Test
    public void queryParamsStringGivenForTutsPlusRequest() {
        assertEquals("var1=this%20is%20some%20string", tutsPlusRequest.queryParamsString());
    }

    @Test
    public void theVerbIsPutForAPutRequest() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        assertEquals("PUT", request.verb());
    }

    @Test
    public void putRequestsHaveABody() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        params.put("Content-Length", "15");
        params.put("Body", "This is my body");
        Request request = new Request(params);
        assertEquals("This is my body", request.body());
    }

    @Test
    public void shortRequestReturnsFalseForBadRequest() {
        assertEquals(false, shortRequest.isBadRequest());
    }

    @Test
    public void tutsPlusRequestReturnsFalseForBadRequest() {
        assertEquals(false, tutsPlusRequest.isBadRequest());
    }

    @Test
    public void emptyRequestCanBeSetAsABadRequest() {
        emptyRequest.setAsBadRequest();
        assertEquals(true, emptyRequest.isBadRequest());
    }

    @Test
    public void putRequestsHaveAContentLength() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "PUT");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        params.put("Content-Length", "15");
        params.put("Body", "This is my body");
        Request request = new Request(params);
        assertEquals("15", request.contentLength());
    }

    @Test
    public void getRequestsDoNotHaveAContentLength() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Verb", "GET");
        params.put("URL", "/");
        params.put("Protocol", "HTTP/1.1");
        Request request = new Request(params);
        assertEquals("", request.contentLength());
    }

}
