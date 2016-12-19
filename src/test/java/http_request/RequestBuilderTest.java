package http_request;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RequestBuilderTest {

    private RequestBuilder builder;

    @Before
    public void setup() {
        this.builder = new RequestBuilder();
    }

    @Test
    public void itReturnsABlankRequestIfItIsPassedNoOptions() {
        assertEquals("", this.builder.build().verb());
        assertEquals("", this.builder.build().url());
        assertEquals("", this.builder.build().contentLength());
    }

    @Test
    public void itAddsAVerb() {
        assertEquals("VERB", this.builder.addVerb("VERB").build().verb());
    }

    @Test
    public void itAddsAUrl() {
        assertEquals("/baz", this.builder.addUrl("/baz").build().url());
    }

    @Test
    public void itAddsAProtocol() {
        assertEquals("HTTP/1.1", this.builder.addProtocol("HTTP/1.1").build().protocol());
    }

    @Test
    public void itAddsAHost() {
        assertEquals("My Host", this.builder.addHost("My Host").build().host());
    }

    @Test
    public void itAddsAUserAgent() {
        assertEquals("Agent", this.builder.addUserAgent("Agent").build().userAgent());
    }

    @Test
    public void itAddsAnAcceptParam() {
        assertEquals("true", this.builder.addAccept("true").build().accept());
    }

    @Test
    public void itAddsAContentLengthAsAnInteger() {
        assertEquals("12", this.builder.addContentLength(12).build().contentLength());
    }

    @Test
    public void itAddsAnAcceptedLanguage() {
        assertEquals("German", this.builder.addAcceptLanguage("German").build().acceptLanguage());
    }

    @Test
    public void itAddsAnAcceptedEncoding() {
        assertEquals("asci", this.builder.addAcceptEncoding("asci").build().acceptEncoding());
    }

    @Test
    public void itAddsAnAcceptedCharset() {
        assertEquals("abc", this.builder.addAcceptCharset("abc").build().acceptCharset());
    }

    @Test
    public void itAddsAKeepAliveParameter() {
        assertEquals("true", this.builder.addKeepAlive("true").build().keepAlive());
    }

    @Test
    public void itAddsAConnection() {
        assertEquals("connection", this.builder.addConnection("connection").build().connection());
    }

    @Test
    public void itAddsACookie() {
        assertEquals("chocolate chip", this.builder.addCookie("chocolate chip").build().cookie());
    }

    @Test
    public void itAddsPragma() {
        assertEquals("pragma value", this.builder.addPragma("pragma value").build().pragma());
    }

    @Test
    public void itAddsCacheControl() {
        assertEquals("some value", this.builder.addCacheControl("some value").build().cacheControl());
    }

    @Test
    public void itAddsABody() {
        assertEquals("this is my body", this.builder.addBody("this is my body").build().body());
    }

    @Test
    public void itAddsAContentLength() {
        assertEquals("5", this.builder.addContentLength("5").build().contentLength());
    }

    @Test
    public void itAddsQueryParams() {
        assertEquals("params", this.builder.addQueryParams("params").build().queryParamsString());
    }

    @Test
    public void itAddsIfNoneMatch() {
        assertEquals("bar", this.builder.addIfNoneMatch("bar").build().ifNoneMatch());
    }

    @Test
    public void itChainsMultipleAdditions() {
        Request request = this.builder.addVerb("Foo").addUrl("Bar").addContentLength(5).addUserAgent("quo").build();
        assertEquals("Foo", request.verb());
        assertEquals("Bar", request.url());
        assertEquals("5", request.contentLength());
        assertEquals("quo", request.userAgent());
    }

}
