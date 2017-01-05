package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;

import static junit.framework.TestCase.assertEquals;

public class QueryParametersActionTest {

    private QueryParametersAction action;
    private DataTable dataTable;

    @Before
    public void setup() {
        this.action = new QueryParametersAction();
        this.dataTable = new DataTable();
    }

    @Test
    public void itAddsSpacesToVariableOne() {
        Request request = new RequestBuilder().addVerb("GET").addProtocol("HTTP/1.1").addUrl("/foo").addQueryParams("1=O").build();
        this.action.execute(request, this.dataTable);
        assertEquals("1 = O", this.dataTable.retrieveBody("/foo"));
    }

    @Test
    public void itAddsSpacesToVariableTwo() {
        Request request = new RequestBuilder().addVerb("GET").addProtocol("HTTP/1.1").addUrl("/foo").addQueryParams("2=s").build();
        this.action.execute(request, this.dataTable);
        assertEquals("2 = s", this.dataTable.retrieveBody("/foo"));
    }

    @Test
    public void itAddsSpacesAppropriatelyToAString() {
        Request request = new RequestBuilder().addVerb("GET").addProtocol("HTTP/1.1").addUrl("/foo").addQueryParams("=12=s1=Oabc").build();
        this.action.execute(request, this.dataTable);
        assertEquals("=12 = s1 = Oabc", this.dataTable.retrieveBody("/foo"));
    }
}
