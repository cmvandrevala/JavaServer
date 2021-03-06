package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;

import static junit.framework.TestCase.assertTrue;

public class ReadFromFileActionTest {

    private DataTable dataTable;
    private PathToUrlMapper mapper;
    private ReadFromFileAction action;

    @Before
    public void setup() {
        this.dataTable = new DataTable();
        this.mapper = new PathToUrlMapper("public/");
        this.action = new ReadFromFileAction(this.mapper);
    }

    @Test
    public void aJPEGImageHasANonZeroContentLength() {
        Request request = new RequestBuilder().addUrl("/image.jpeg").addVerb("GET").addProtocol("HTTP/1.1").build();
        action.execute(request, this.dataTable);
        assertTrue(dataTable.retrieveBody("/image.jpeg").length() > 0);
    }

    @Test
    public void aPNGImageHasANonZeroContentLength() {
        Request request = new RequestBuilder().addUrl("/image.png").addVerb("GET").addProtocol("HTTP/1.1").build();
        action.execute(request, this.dataTable);
        assertTrue(dataTable.retrieveBody("/image.png").length() > 0);
    }

    @Test
    public void aGIFImageHasANonZeroContentLength() {
        Request request = new RequestBuilder().addUrl("/image.gif").addVerb("GET").addProtocol("HTTP/1.1").build();
        action.execute(request, this.dataTable);
        assertTrue(dataTable.retrieveBody("/image.gif").length() > 0);
    }

}
