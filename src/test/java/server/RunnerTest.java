package server;

import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunnerTest {

    private RoutesTable routesTable;
    private DataTable dataTable;

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
        this.dataTable = new DataTable();
    }

    @Test
    public void itSetsAPort() throws IOException {
        Runner runner = new Runner(100, this.routesTable, this.dataTable);
        assertEquals(100, runner.portNumber);
    }

    @Test
    public void itUsesTenThreadsByDefault() throws IOException {
        Runner runner = new Runner(5000, this.routesTable, this.dataTable);
        assertEquals(10, runner.numberOfThreads);
    }

    @Test
    public void theRunnerIsStatusIsNotStoppedUponInitialization() throws IOException {
        Runner runner = new Runner(5000, this.routesTable, this.dataTable);
        assertFalse(runner.isStopped());
    }

    @Test
    public void theRunnerStatusCanBeChanged() throws IOException {
        Runner runner = new Runner(5000, this.routesTable, this.dataTable);
        runner.stop();
        assertTrue(runner.isStopped());
    }

}
