package dao;

import models.Individual;
import models.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juan on 29/08/16.
 */


public class DatabaseConnectionTest {

    private DatabaseConnection dbconn;
    private Individual individual;
    private Task task;


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
        task = new Task("TestTask","Extrainfo",1,0,1);
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
        if (dbconn.getIndividual(individual.getId()) == null) {
            assertTrue("Could not add individual to DB", dbconn.addIndividual(individual));
        } else {
            assertFalse("Could not add individual to DB", dbconn.addIndividual(individual));
        }
    }

    @Test
    public void testUpdateIndividual() {
        individual.setInfo("C++");
        assertTrue("Could not update individual in DB", dbconn.updateIndividual(individual));
    }

    @Test
    public void testGetIndividual() {
        int persId = 7777777;
        assertNotNull("Couldn't get individual from DB", dbconn.getIndividual(persId));
    }

    // Tasks

    @Test
    public void testInsertTask() {
        assertTrue("Could not add task to DB", dbconn.addTask(task));
    }

    @Test
    public void testUpdateTask() {
        task.setInfo("Changed info");
        assertTrue("Could not update task in DB", dbconn.updateTask(task));
    }

    @Test
    public void testGetTask() {
        assertNotNull("Couldn't get specific task", dbconn.getTask(1));
    }
}