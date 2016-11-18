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
        assertEquals(new File("cob_spec/public"), mapper.publicDirectory);
    }

    @Test
    public void thereIsARootDirectory() {
        assertEquals(new File("www"), mapper.rootDirectory);
    }

    @Test
    public void anEmptyUrlReturnsTheIndex() {
        assertEquals(new File("www/index.html"), mapper.fileCorrespondingToUrl(""));
    }

    @Test
    public void aSingleSlashReturnsTheIndex() {
        assertEquals(new File("www/index.html"), mapper.fileCorrespondingToUrl("/"));
    }

    @Test
    public void fooMapsToAFile() {
        assertEquals(new File("www/foo.html"), mapper.fileCorrespondingToUrl("/foo"));
    }

    @Test
    public void aLongerUrlMapsToAFile() {
        assertEquals(new File("www/foo/bar/baz/quo.html"), mapper.fileCorrespondingToUrl("/foo/bar/baz/quo"));
    }

    @Test
    public void periodsAreIgnoredAndTheUserIsSentToTheIndex() {
        assertEquals(new File("www/index.html"), mapper.fileCorrespondingToUrl("/../../../quo"));
    }

    @Test
    public void file1MapsToAPublicResource() {
        assertEquals(new File("cob_spec/public/file1"), mapper.fileCorrespondingToUrl("/file1"));
    }

    @Test
    public void file2MapsToAPublicResource() {
        assertEquals(new File("cob_spec/public/file2"), mapper.fileCorrespondingToUrl("/file2"));
    }

    @Test
    public void gifFilesMapToAPublicResource() {
        assertEquals(new File("cob_spec/public/image.gif"), mapper.fileCorrespondingToUrl("/image.gif"));
    }

}
