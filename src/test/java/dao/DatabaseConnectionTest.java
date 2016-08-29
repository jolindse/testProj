package dao;

import models.Individual;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juan on 29/08/16.
 */


public class DatabaseConnectionTest {

    private DatabaseConnection dbconn;
    private Individual individual;

    // Database connection tests

    @Test
    public void testDatabaseConnectionExists() {
        DatabaseConnection dbconn2 = new DatabaseConnection();
        assertNotNull("Error creating DatabaseConnection",dbconn2);
    }

    @Before
    public void init() {
        dbconn = new DatabaseConnection();
        individual = new Individual("Mattias", "Larsson", "Java",7777777);
    }

    @Test
    public void testDatabaseConnWorks() {
        assertTrue("Can not establish connection to DB", dbconn.isConnected());
    }

    @Test
    public void testCloseDBconn() {
        assertTrue("Can not close DB connection", dbconn.closeConn());
    }

    // DB READ WRITE ACCESS

    // Individual

    @Test
    public void testInsertIndividual() {
        assertTrue("Could not add individual to DB",dbconn.addIndividual(individual));
    }

    @Test
    public void testUpdateIndividual() {
        individual.setInfo("C++");
        assertTrue("Could not update individual in DB", dbconn.updateIndividual(individual));
    }

    @Test
    public void testGetIndividual() {
        int persId = 7777777;
        assertNotNull("Couldnt get indivdual from DB", dbconn.getIndividual(persId));
    }


}