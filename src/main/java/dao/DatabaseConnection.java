package dao;

import models.Individual;
import models.Story;
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
                        "prio INTEGER," +
                        "sprint INTEGER)" +
                        "story INTEGER);",
                "CREATE TABLE IF NOT EXISTS individuals " +
                        "(id INTEGER NOT NULL PRIMARY KEY," +
                        "firstName TEXT," +
                        "lastName INTEGER," +
                        "info TEXT);",
                "CREATE TABLE IF NOT EXISTS stories " +
                        "(id INTEGER PRIMARY KEY," +
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

    /**
     * Removes specific individual from DB
     *
     * @param persId int
     * @return boolean
     */
    public boolean removeIndividual(int persId) {
        String sql = "DELETE FROM individuals WHERE id = "+persId+";";
        return executeSQL(sql);
    }

    // Tasks

    /**
     * Adds a task to DB
     *
     * @param task Task
     * @return boolean
     */
    public Task addTask(Task task) {
        String name = task.getName();
        String info = task.getInfo();
        int individual = 0;
        int status = task.getStatus();
        int prio = 0;
        int sprint = task.getSprint();
        int story = task.getStory();

        String sql = String.format("INSERT INTO tasks (name, info, status, prio, sprint, story) VALUES ('%s','%s',%s,%s,%s,%s);",
                name, info, status, prio, sprint, story);
        if(executeSQL(sql)){
            sql = "SELECT MAX(id) AS newid FROM tasks;";
            ArrayList<HashMap> currRes = executeSQLQuery(sql);
            HashMap currMap = (HashMap) currRes.get(0);
            int id = (int) currMap.get("newid");
            task.setId(id);
        }
        return task;
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
            sql = String.format("UPDATE tasks SET name='%s', info='%s', status=%s, prio=%s, story=%s, sprint=%s WHERE id = %s;",
                    task.getName(),
                    task.getInfo(),
                    task.getStatus(),
                    task.getPrio(),
                    task.getStory(),
                    task.getSprint(),
                    taskDBid);
            if (executeSQLUpdate(sql) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get specific task from DB
     *
     * @param id int
     * @return Task
     */
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
            currTask.setStory((int)currMap.get("story"));
            currTask.setSprint((int)currMap.get("sprint"));
            currTask.setId((int)currMap.get("id"));
        }
        return currTask;
    }

    /**
     * Deletes a task from DB
     *
     * @param id int
     * @return boolean
     */
    public boolean removeTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = "+id+";";
        return executeSQL(sql);
    }

    // Stories

    /**
     * Adds a story to DB
     *
     * @param story Story
     * @return boolean
     */
    public boolean addStory(Story story) {
        String sql = String.format("INSERT INTO stories (id, text, info) VALUES (%s,'%s','%s');",
                story.getNumber(),story.getText(),story.getInfo());
        return executeSQL(sql);
    }

    /**
     * Updates a story in DB
     *
     * @param story Story
     * @return boolean
     */
    public boolean updateStory(Story story) {
        String sql = String.format("UPDATE stories SET text='%s', info='%s' WHERE id = %s;",
        story.getText(), story.getInfo(), story.getNumber());
        if (executeSQLUpdate(sql) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets specific story from DB
     *
     * @param number int
     * @return Story
     */
    public Story getStory(int number) {
        Story currStory = null;
        String sql = "SELECT * FROM stories WHERE id = "+number+";";
        ArrayList<HashMap> currRes = executeSQLQuery(sql);
        if (currRes.size() > 0) {
            HashMap currMap = (HashMap) currRes.get(0);
            currStory = new Story();
            currStory.setText((String)currMap.get("text"));
            currStory.setInfo((String)currMap.get("info"));
            currStory.setNumber((int)currMap.get("id"));
        }
        return currStory;
    }

    /**
     * Deletes a story from DB
     *
     * @param number int
     * @return boolean
     */
    public boolean removeStory(int number) {
        String sql = "DELETE FROM stories WHERE id = "+number+";";
        return executeSQL(sql);
    }

}
