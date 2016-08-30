package dao;

import models.Individual;
import models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Database connection and commands class.
 *
 * Created by juan on 29/08/16.
 */
public class DatabaseConnection {

    private Connection conn;
    private boolean connected;


	/**
     * Constructor that enables database connection.
     */
    public DatabaseConnection() {
        connected = false;
        String path = "kanban.db";
        // db parameters
        String url = "jdbc:sqlite:" + path;
        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
            connected = true;
            initDB();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getStackTrace());
        }
    }

	/**
     * Returns connection status
     *
     * @return boolean
     */
    public boolean isConnected() {
        return connected;
    }

	/**
	 *
     * Gracefully closes database connection.
     *
     * @return boolean.
     */
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

	/**
     * Initializes database with table creation if previously not created.
     */
    private void initDB() {
        String sqlInit[] = {
                "CREATE TABLE IF NOT EXISTS tasks " +
                        "(id INTEGER NOT NULL PRIMARY KEY," +
                        "name TEXT," +
                        "info TEXT," +
                        "status INTEGER," +
                        "prio INTEGER);",
                "CREATE TABLE IF NOT EXISTS individuals " +
                        "(id INTEGER NOT NULL PRIMARY KEY," +
                        "firstName TEXT," +
                        "lastName INTEGER," +
                        "info TEXT);",
                "CREATE TABLE IF NOT EXISTS stories " +
                        "(id INTEGER PRIMARY KEY," +
                        "storyNumber INTEGER," +
                        "text TEXT, " +
                        "info TEXT);",
                "CREATE TABLE IF NOT EXISTS tasksindividuals " +
                        "(id INTEGER PRIMARY KEY, " +
                        "taskid INTEGER REFERENCES tasks(id) ON DELETE SET NULL, " +
                        "individualid INTEGER REFERENCES individuals(id) ON DELETE SET NULL);",
                "CREATE TABLE IF NOT EXISTS tasksstories " +
                        "(id INTEGER PRIMARY KEY, " +
                        "taskid INTEGER REFERENCES tasks(id) ON DELETE SET NULL, " +
                        "storyid INTEGER REFERENCES stories(id) ON DELETE SET NULL);"
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
                return false;
                //e.printStackTrace();
            } finally {
                try {
                    if (stmnt != null) {
                        stmnt.close();
                    }
                } catch (SQLException e) {
                    return false;
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
    private ArrayList executeSQLQuery(String sql) {
        ArrayList returnList = null;
        Statement stmnt = null;
        ResultSet currRs = null;
        if (conn != null) {
            try {
                stmnt = conn.createStatement();
                currRs = stmnt.executeQuery(sql);
                returnList = resultSetToArrayList(currRs);
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
        return returnList;
    }

	/**
     * Executes a query and returns the number of rows affected.
     *
     * @param sql String
     * @return int
     */
    private int executeSQLUpdate(String sql) {
        Statement stmnt = null;
        int results = 0;

        try {
            stmnt = conn.createStatement();
            results = stmnt.executeUpdate(sql);
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

        }
        return results;
    }

    /**
     * Converts a resultset to a ArrayList of HashMaps.
     *
     * @param rs ResultSet
     * @return ArrayList<HashMap>
     */
    private ArrayList resultSetToArrayList(ResultSet rs) {
        ResultSetMetaData md = null;
        ArrayList list = null;
        try {
            md = rs.getMetaData();
            int columns = md.getColumnCount();
            list = new ArrayList(50);
            while (rs.next()) {
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // SPECIFIC DB CALLS

    // Individuals

    /**
     * Adds a individual to database
     *
     * @param individual
     * @return
     */
    public boolean addIndividual(Individual individual) {
        String firstName = individual.getFirstName();
        String lastName = individual.getLastName();
        String info = individual.getInfo();
        int id = individual.getId();

        String sql = String.format("INSERT INTO individuals (id,firstName, lastName, info) VALUES(%s,'%s','%s','%s');",
             id,firstName,lastName,info);
        return executeSQL(sql);
    }

    /**
     * Updates a individual in database
     *
     * @param individual
     * @return
     */
    public boolean updateIndividual(Individual individual) {
        String firstName = individual.getFirstName();
        String lastName = individual.getLastName();
        String info = individual.getInfo();
        int persId = individual.getId();

        String sql = String.format("UPDATE individuals SET firstName ='%s',lastname='%s',info='%s' WHERE id = %s;",
                firstName, lastName, info, persId);

        if (executeSQLUpdate(sql) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Get specific individual from DB
     *
     * @param persId int
     * @return individual Individual
     */
    public Individual getIndividual(int persId) {
        String sql = "SELECT * FROM individuals WHERE id = " + persId + ";";
        Individual currInd = null;
        ArrayList<HashMap> currRes = executeSQLQuery(sql);
        if (currRes.size() > 0) {
            HashMap currMap = (HashMap) currRes.get(0);
            currInd = new Individual((String) currMap.get("firstName"), (String) currMap.get("lastName"), (String) currMap.get("info"), (int) currMap.get("id"));
        }
        return currInd;
    }

    // Tasks

    /**
     * Adds a task to DB
     *
     * @param task Task
     * @return boolean
     */
    public boolean addTask(Task task) {
        String name = task.getName();
        String info = task.getInfo();
        int individual = 0;
        int status = 0;
        int prio = 0;

        String sql = String.format("INSERT INTO tasks (name, info, status, prio) VALUES ('%s','%s',%s,%s);",
                name, info, status, prio);
        return executeSQL(sql);
    }

    /**
     * Updates a existing task in DB
     *
     * @param task Task
     * @return boolean
     */
    public boolean updateTask(Task task) {
        int taskDBid = 0;
        String sql = "SELECT * FROM tasks WHERE name = '"+task.getName()+"';";
        ArrayList<HashMap> currRes = executeSQLQuery(sql);
        if (currRes.size() > 0) {
            HashMap currMap = (HashMap) currRes.get(0);
            taskDBid = (int) currMap.get("id");
            sql = String.format("UPDATE tasks SET name='%s', info='%s', status=%s, prio=%s WHERE id = %s;",
                    task.getName(),
                    task.getInfo(),
                    task.getStatus(),
                    task.getPrio(),
                    taskDBid);
            if (executeSQLUpdate(sql) > 0) {
                return true;
            }
        }
        return false;
    }

    public Task getTask(int id) {
        Task currTask = null;
        String sql = "SELECT * FROM tasks WHERE id = "+id+";";
        ArrayList<HashMap> currRes = executeSQLQuery(sql);
        if (currRes.size() > 0) {
            HashMap currMap = (HashMap) currRes.get(0);
            currTask = new Task();
            currTask.setName((String)currMap.get("name"));
            currTask.setInfo((String)currMap.get("info"));
            currTask.setPrio((int)currMap.get("prio"));
            currTask.setStatus((int)currMap.get("status"));
            currTask.setId((int)currMap.get("id"));
        }
        return currTask;
    }

}
