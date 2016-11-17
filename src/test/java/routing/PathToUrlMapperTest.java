package routing;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PathToUrlMapperTest {

    PathToUrlMapper mapper;

    @Before
    public void setup() {
        this.mapper = new PathToUrlMapper();
    }

    @Test
    public void thereIsAPublicDirectoryForAssets() {
        assertEquals("cob_spec/public", mapper.publicDirectory);
    }

    @Test
    public void thereIsARootDirectory() {
        assertEquals("www", mapper.rootDirectory);
    }

    @Test
    public void thePublicDirectoryCanBeUpdated() {
        mapper.publicDirectory = "foo";
        assertEquals("foo", mapper.publicDirectory);
    }

    @Test
    public void theRootDirectoryCanBeUpdated() {
        mapper.rootDirectory = "bar";
        assertEquals("bar", mapper.rootDirectory);
    }

    @Test
    public void anEmptyUrlReturnsTheIndex() {
        assertEquals(new File("www/index.html"), mapper.pathCorrespondingToUrl(""));
    }

    @Test
    public void aSingleSlashReturnsTheIndex() {
        assertEquals(new File("www/index.html"), mapper.pathCorrespondingToUrl("/"));
    }

    @Test
    public void fooMapsToAFile() {
        assertEquals(new File("www/foo.html"), mapper.pathCorrespondingToUrl("/foo"));
    }

    @Test
    public void barMapsToAFile() {
        assertEquals(new File("www/bar.html"), mapper.pathCorrespondingToUrl("/bar"));
    }

    @Test
    public void aLongerUrlMapsToAFile() {
        assertEquals(new File("www/foo/bar/baz/quo.html"), mapper.pathCorrespondingToUrl("/foo/bar/baz/quo"));
    }

    @Test
    public void periodsAreIgnoredAndTheUserIsSentToTheIndex() {
        assertEquals(new File("www/index.html"), mapper.pathCorrespondingToUrl("/../../../quo"));
    }

}
