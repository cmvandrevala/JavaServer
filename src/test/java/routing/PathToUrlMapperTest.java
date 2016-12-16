package routing;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PathToUrlMapperTest {

    private PathToUrlMapper mapper;

    @Before
    public void setup() {
        this.mapper = new PathToUrlMapper();
    }

    @Test
    public void thereIsAPublicDirectoryForAssets() {
        assertTrue(mapper.publicDirectory.getAbsolutePath().contains("public"));
    }

    @Test
    public void anEmptyUrlReturnsTheIndex() {
        assertTrue(mapper.fileCorrespondingToUrl("").getAbsolutePath().contains("/public/index.html"));
    }

    @Test
    public void aSingleSlashReturnsTheIndex() {
        assertTrue( mapper.fileCorrespondingToUrl("/").getAbsolutePath().contains("/public/index.html"));
    }

    @Test
    public void periodsAreIgnoredAndTheUserIsSentToTheIndex() {
        assertTrue(mapper.fileCorrespondingToUrl("/../../../quo").getAbsolutePath().contains("/public/index.html"));
    }

    @Test
    public void file1MapsToAPublicResource() {
        assertTrue(mapper.fileCorrespondingToUrl("/file1").getAbsolutePath().contains("/public/file1"));
    }

    @Test
    public void gifFilesMapToAPublicResource() {
        assertTrue(mapper.fileCorrespondingToUrl("/image.gif").getAbsolutePath().contains("/public/image.gif"));
    }

}
