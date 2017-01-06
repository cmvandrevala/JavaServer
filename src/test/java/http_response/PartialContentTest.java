package http_response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PartialContentTest {

    @Test
    public void returnsAnEmptyStringForEmptyContent() {
        assertThat(generateBody("", null, null), is(equalTo("")));
    }


    @Test
    public void returnsTheWholeContentForAFullRange() {
        assertThat(generateBody("A", 0, 0), is(equalTo("A")));
    }

    @Test
    public void returnsTheFirstCharacter() {
        assertThat(generateBody("ABCD", 0, 0), is(equalTo("A")));
    }

    @Test
    public void returnsTheSecondCharacter() {
        assertThat(generateBody("ABCD", 1, 1), is(equalTo("B")));
    }

    @Test
    public void returnsMultipleCharacters() {
        assertThat(generateBody("ABCD", 1, 2), is(equalTo("BC")));
    }

    @Test
    public void returnsTheRestOfTheStringWhenNoUpperBoundIsSpecified() {
        assertThat(generateBody("ABCD", 1, null), is(equalTo("BCD")));
    }

    @Test
    public void returnsTheFinalCharactersOfTheString() {
        assertThat(generateBody("ABCD", null, 3), is(equalTo("BCD")));
    }

    @Test
    public void returnsTheFinalCharactersOfALongString() {
        assertThat(generateBody("ABCDEFGHIJ", null, 3), is(equalTo("HIJ")));
    }

    @Test
    public void returnsTheEntireStringForNoBounds() {
        assertThat(generateBody("ABCD", null, null), is(equalTo("ABCD")));
    }

    @Test
    public void returnsToTheEndOfTheStringWithALowerBound() {
        assertThat(generateBody("0123456789", 7, 9), is(equalTo("789")));
    }

    private String generateBody(String content, Integer lowerBound, Integer upperBound) {
        return new PartialContent(content, lowerBound, upperBound).body();
    }

}
