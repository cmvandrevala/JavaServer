package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.PathToUrlMapper;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class PartialResponseTest {

    private PartialResponse partialResponse;

    @Before
    public void setup() {
        this.partialResponse = new PartialResponse(new PathToUrlMapper("public/"));
    }

    @Test
    public void itDetectsALowerBoundWhenThereAreTwoBounds() {
        Request request = new RequestBuilder().addRange("bytes=5-6").build();
        assertTrue(partialResponse.containsLowerBound(request));
    }

    @Test
    public void itDetectsALowerBoundWhenTherIsOneLowerBound() {
        Request request = new RequestBuilder().addRange("bytes=12-").build();
        assertTrue(partialResponse.containsLowerBound(request));
    }

    @Test
    public void itDoesNotDetectALowerBoundWhenThereIsOneUpperBound() {
        Request request = new RequestBuilder().addRange("bytes=-8").build();
        assertFalse(partialResponse.containsLowerBound(request));
    }

    @Test
    public void itDetectsAnUpperBoundWhenThereAreTwoBounds() {
        Request request = new RequestBuilder().addRange("bytes=5-6").build();
        assertTrue(partialResponse.containsUpperBound(request));
    }

    @Test
    public void itDetectsAnUpperBoundWhenTherIsOneUpperBound() {
        Request request = new RequestBuilder().addRange("bytes=-8").build();
        assertTrue(partialResponse.containsUpperBound(request));
    }

    @Test
    public void itDoesNotDetectAnUpperBoundWhenThereIsOneLowerBound() {
        Request request = new RequestBuilder().addRange("bytes=12-").build();
        assertFalse(partialResponse.containsUpperBound(request));
    }

    @Test
    public void itReturnsALowerBoundGivenTwoBounds() {
        Request request = new RequestBuilder().addRange("bytes=5-6").build();
        assertEquals(5, partialResponse.lowerBound(request));
    }

    @Test
    public void itReturnsALowerBoundGivenTwoBoundsSecondExample() {
        Request request = new RequestBuilder().addRange("bytes=12-19").build();
        assertEquals(12, partialResponse.lowerBound(request));
    }

    @Test
    public void itReturnsAnUpperBoundGivenTwoBounds() {
        Request request = new RequestBuilder().addRange("bytes=5-6").build();
        assertEquals(6, partialResponse.upperBound(request));
    }

    @Test
    public void itReturnsAnUpperBoundGivenTwoBoundsSecondExample() {
        Request request = new RequestBuilder().addRange("bytes=12-19").build();
        assertEquals(19, partialResponse.upperBound(request));
    }

    @Test
    public void itReturnsALowerBoundGivenTheUpperBoundIsMissing() {
        Request request = new RequestBuilder().addRange("bytes=4-").build();
        assertEquals(4, partialResponse.lowerBound(request));
    }

    @Test
    public void itCalculatesAnUpperBoundWhenNoUpperBoundIsGiven() {
        Request request = new RequestBuilder().addRange("bytes=4-").addUrl("/partial_content.txt").build();
        assertEquals(76, partialResponse.upperBound(request));
    }

    @Test
    public void itCalculatesALowerBoundWhenNoLowerBoundIsGiven() {
        Request request = new RequestBuilder().addRange("bytes=-6").addUrl("/partial_content.txt").build();
        assertEquals(71, partialResponse.lowerBound(request));
    }

    @Test
    public void itReturnsAnUpperBoundGivenTheLowerBoundIsMissing() {
        Request request = new RequestBuilder().addRange("bytes=-6").build();
        assertEquals(76, partialResponse.upperBound(request));
    }

}
