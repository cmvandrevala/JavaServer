package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class ResponseBuilderTest {

    private ResponseBuilder builder;

    @Before
    public void setup() {
        this.builder = new ResponseBuilder();
    }

    @Test
    public void itAddsAProtocol() {
        assertEquals("HTTP/1.1", this.builder.addProtocol("HTTP/1.1").build().protocol());
    }

    @Test
    public void itAddsAStatusCode() {
        assertEquals(200, this.builder.addStatusCode(200).build().statusCode());
    }

    @Test
    public void itAddsAStatusMessage() {
        assertEquals("OK", this.builder.addStatusMessage("OK").build().statusMessage());
    }

    @Test
    public void itAddsAContentType() {
        assertEquals("text/html", this.builder.addContentType("text/html").build().contentType());
    }

    @Test
    public void itAddsAConnection() {
        assertEquals("close", this.builder.addConnection("close").build().connection());
    }

    @Test
    public void itAddsABody() {
        assertEquals("foo", this.builder.addBody("foo").build().body());
    }

    @Test
    public void itAddsASetCookie() {
        assertEquals("cookie", this.builder.addSetCookie("cookie").build().setCookie());
    }

    @Test
    public void itAddsALocation() {
        assertEquals("location", this.builder.addLocation("location").build().location());
    }

    @Test
    public void itAddsAllow() {
        assertEquals("foo", this.builder.addAllow("foo").build().allow());
    }

    @Test
    public void itAddsAnETag() {
        assertEquals("abcde", this.builder.addETag("abcde").build().etag());
    }

    @Test
    public void itAddsAContentLocation() {
        assertEquals("/bar.md", this.builder.addContentLocation("/bar.md").build().contentLocation());
    }

    @Test
    public void itAddsAContentRange() {
        assertEquals("0-999", this.builder.addContentRange("0-999").build().contentRange());
    }

    @Test
    public void itAddsAWWWAuthenticate() {
        assertEquals("Basic realm=foo", this.builder.addWWWAuthenticate("Basic realm=foo").build().wwwAuthenticate());
    }

    @Test
    public void itCreatesADefault400Response() {
        Response response = ResponseBuilder.default400Response();
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(400, response.statusCode());
        assertEquals("Bad Request", response.statusMessage());
        assertEquals("", response.body());
        assertEquals("0", response.contentLength());
        assertEquals("close", response.connection());
    }

    @Test
    public void itCreatesADefault401Response() {
        Response response = ResponseBuilder.default401Response("my-realm");
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(401, response.statusCode());
        assertEquals("Unauthorized", response.statusMessage());
        assertEquals("", response.body());
        assertEquals("0", response.contentLength());
        assertEquals("close", response.connection());
        assertEquals("Basic realm=\"my-realm\"", response.wwwAuthenticate());
    }

    @Test
    public void itCreatesADefault404Response() {
        Response response = ResponseBuilder.default404Response();
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(404, response.statusCode());
        assertEquals("Not Found", response.statusMessage());
        assertEquals("", response.body());
        assertEquals("0", response.contentLength());
        assertEquals("close", response.connection());
    }

    @Test
    public void itCreatesADefault405Response() {
        Response response = ResponseBuilder.default405Response();
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(405, response.statusCode());
        assertEquals("Method Not Allowed", response.statusMessage());
        assertEquals("", response.body());
        assertEquals("0", response.contentLength());
        assertEquals("close", response.connection());
    }

    @Test
    public void itCreatesADefault411Response() {
        Response response = ResponseBuilder.default411Response();
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(411, response.statusCode());
        assertEquals("Length Required", response.statusMessage());
        assertEquals("", response.body());
        assertEquals("0", response.contentLength());
        assertEquals("close", response.connection());
    }

    @Test
    public void itCreatesADefault418Response() {
        Response response = ResponseBuilder.default418Response();
        assertEquals("HTTP/1.1", response.protocol());
        assertEquals(418, response.statusCode());
        assertEquals("I'm a teapot", response.statusMessage());
        assertEquals("110", response.contentLength());
        assertEquals("close", response.connection());
    }

    @Test
    public void theBodyOfThe418ResponseIsCorrect() {
        Response response = ResponseBuilder.default418Response();
        assertEquals("I'm a teapot" + FormattedStrings.CRLF +
                "             ;,'" + FormattedStrings.CRLF +
                "     _o_    ;:;'" + FormattedStrings.CRLF +
                " ,-.'---`.__ ;" + FormattedStrings.CRLF +
                "((j`=====',-'" + FormattedStrings.CRLF +
                " `-\\     /" + FormattedStrings.CRLF +
                "    `-=-'     hjw", response.body());
    }

}
