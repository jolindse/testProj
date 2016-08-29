package dao;

import models.Individual;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juan on 29/08/16.
 */
public class DatabaseConnection {

    private Connection conn;
    private boolean connected;

    public DatabaseConnection() {

        connected = false;

        String path = "kanban.db";
        // db parameters
        String url = "jdbc:sqlite:"+path;
        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
            connected = true;
            initDB();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getStackTrace());
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean closeConn() {

        boolean closed = false;

        try {
            conn.close();
            closed = true;
            connected = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return closed;
    }

    private void initDB() {
        String sqlInit[] = {
                "CREATE TABLE IF NOT EXISTS tasks (id INTEGER NOT NULL PRIMARY KEY,name TEXT,info TEXT,individual INTEGER, status INTEGER, story INTEGER, prio INTEGER);",
                "CREATE TABLE IF NOT EXISTS individuals (id INTEGER NOT NULL PRIMARY KEY,firstName TEXT,lastName INTEGER,info TEXT);",
                "CREATE TABLE IF NOT EXISTS stories (id INTEGER PRIMARY KEY,storyNumber INTEGER,text TEXT, info TEXT);"
        };
        for (String currSQL : sqlInit) {
            executeSQL(currSQL);
        }
    }

    /**
     * Executes a SQL-query that doesn't return useful information.
     *
     * @param sql String
     */
    private boolean executeSQL(String sql) {

        if (conn != null) {
            Statement stmnt = null;
            try {
                stmnt = conn.createStatement();
                stmnt.execute(sql);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmnt != null) {
                        stmnt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No connection to database.");
        }
        return false;
    }

    /**
     * Executes a SQL-query that returns information from the database.
     *
     * @param sql String
     * @return ArrayList<HashMap>
     */
    private ResultSet executeSQLQuery(String sql) {

        Statement stmnt = null;
        ResultSet currRs = null;
        if (conn != null) {
            try {
                stmnt = conn.createStatement();
                currRs = stmnt.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stmnt != null) {
                    try {
                        stmnt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (currRs != null) {
                    try {
                        currRs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("No connection to database.");
        }
        return currRs;
    }

    // SPECIFIC DB CALLS

    public boolean addIndividual(Individual individual) {
        String firstName = individual.getFirstName();
        String lastName = individual.getLastName();
        String info = individual.getInfo();
        String sql = "INSERT INTO individuals (firstName, lastName, info) VALUES(\""+firstName+"\",\""+lastName+"\",\""+info+"\");";

        ResultSet results = executeSQLQuery(sql);
        System.out.println("Resulset: "+results); // TEST
        try {
            int id = results.getInt("id");
            System.out.println("Id: "+id); // TEST
            individual.setId(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateIndividual(Individual individual)  {
        String firstName = individual.getFirstName();
        String lastName = individual.getLastName();
        String info = individual.getInfo();
        return true;
    }

}
