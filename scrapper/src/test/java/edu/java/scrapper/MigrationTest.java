package edu.java.scrapper;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigrationTest extends IntegrationEnvironment {

    @Test
    public void migrationRunningTest() {
        assertTrue(IntegrationEnvironment.POSTGRES.isRunning());
    }
}
