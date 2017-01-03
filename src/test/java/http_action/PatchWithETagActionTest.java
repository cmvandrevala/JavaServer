package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;

import static junit.framework.TestCase.assertEquals;

public class PatchWithETagActionTest {

    private PatchWithETagAction action;
    private DataTable table;

    @Before
    public void setup() {
        this.action = new PatchWithETagAction(new PathToUrlMapper("public/"));
        this.table = new DataTable();
    }

    @Test
    public void itProperlyEncodesThePhrasePatchedContent() {
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", this.action.encode("patched content"));
    }

    @Test
    public void itProperlyEncodesThePhraseDefaultContent() {
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", this.action.encode("default content"));
    }

    @Test
    public void itProperlyEncodesARandomString() {
        assertEquals("b1b3773a05c0ed0176787a4f1574ff0075f7521e", this.action.encode("qwerty"));
    }

    @Test
    public void itDoesNotUpdateTheTableIfTheETagMatches() {
        Request request = new RequestBuilder().addIfMatch("b1b3773a05c0ed0176787a4f1574ff0075f7521e").addBody("qwerty").addUrl("/foo.txt").build();
        this.table.addETag("/foo.txt", "b1b3773a05c0ed0176787a4f1574ff0075f7521e");
        this.action.execute(request, this.table);
        assertEquals("b1b3773a05c0ed0176787a4f1574ff0075f7521e", table.retrieveETag("/foo.txt"));
    }

    @Test
    public void itUpdatesTheTableMultipleTimes() {
        this.table.addETag("/patch-content.txt", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec");

        Request requestOne = new RequestBuilder().addIfMatch("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0").addBody("patched content").addUrl("/patch-content.txt").build();
        Request requestTwo = new RequestBuilder().addIfMatch("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec").addBody("default content").addUrl("/patch-content.txt").build();

        this.action.execute(requestOne, this.table);
        this.action.execute(requestTwo, this.table);
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", table.retrieveETag("/patch-content.txt"));
    }
}
