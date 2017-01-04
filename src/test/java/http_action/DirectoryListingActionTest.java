package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;

import static org.junit.Assert.assertTrue;

public class DirectoryListingActionTest {

    private DataTable dataTable;

    @Before
    public void setup() {
        PathToUrlMapper mapper = new PathToUrlMapper("public/");
        this.dataTable = new DataTable();
        DirectoryListingAction action = new DirectoryListingAction(mapper);
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").build();
        action.execute(request, this.dataTable);
    }

    @Test
    public void thePageServesAnHTMLTemplate() {
        assertTrue(this.dataTable.retrieveBody("/").contains("<!DOCTYPE html><html>"));
        assertTrue(this.dataTable.retrieveBody("/").contains("</html>"));
    }

    @Test
    public void itHasTheTitleDirectoryListing() {
        assertTrue(this.dataTable.retrieveBody("/").contains("<head><title>Directory Listing</title></head>"));
    }

    @Test
    public void itListsLinksToFilesInThePublicDirectory() {
        assertTrue(this.dataTable.retrieveBody("/").contains("file1"));
        assertTrue(this.dataTable.retrieveBody("/").contains("file2"));
        assertTrue(this.dataTable.retrieveBody("/").contains("foo.txt"));
        assertTrue(this.dataTable.retrieveBody("/").contains("image.gif"));
        assertTrue(this.dataTable.retrieveBody("/").contains("image.jpeg"));
        assertTrue(this.dataTable.retrieveBody("/").contains("image.png"));
        assertTrue(this.dataTable.retrieveBody("/").contains("partial_content.txt"));
        assertTrue(this.dataTable.retrieveBody("/").contains("patch-content.txt"));
        assertTrue(this.dataTable.retrieveBody("/").contains("text-file.txt"));
    }
}
