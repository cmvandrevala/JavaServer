package http_response;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResponseBuilderTest {

    private ResponseBuilder builder;

    @Before
    public void setup() {
        this.builder = new ResponseBuilder();
    }

    @Test
    public void itReturnsABlankRequestIfItIsPassedNoOptions() {
        assertEquals("", this.builder.build().statusCode());
        assertEquals("", this.builder.build().protocol());
        assertEquals("", this.builder.build().contentType());
    }

    @Test
    public void itAddsAProtocol() {
        assertEquals("HTTP/1.1", this.builder.addProtocol("HTTP/1.1").build().protocol());
    }

    @Test
    public void itAddsAStatusCode() {
        assertEquals("200", this.builder.addStatusCode("200").build().statusCode());
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

}
