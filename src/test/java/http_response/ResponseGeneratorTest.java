package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class ResponseGeneratorTest {

    private DataTable dataTable;
    private ResponseGenerator responseGenerator;

    @Before
    public void setup() {
        this.dataTable = new DataTable();
        this.responseGenerator = new ResponseGenerator(new RoutesTable(), this.dataTable);
    }

    @Test
    public void theBoundsDefaultToTheEntireBody() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").build();
        assertEquals("ABCDEFG", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAStringOverAGivenRange() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=0-4").build();
        assertEquals("ABCD", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAByteRangeSpecFromTheEnd() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=-5").build();
        assertEquals("CDEFG", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAByteRangeSpecFromTheBeginning() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=2-").build();
        assertEquals("CDEFG", responseGenerator.partialContent(request));
    }
}
