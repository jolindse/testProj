package main;

import dao.DatabaseConnection;
import models.Task;

/**
 * Created by juan on 30/08/16.
 */
public class Controller {

    private DatabaseConnection dao;

    public Controller() {
        dao = new DatabaseConnection();
    }

    public Task addTask(Task task) {
        Task completeTask = dao.addTask(task);
        return completeTask;
    }
}
