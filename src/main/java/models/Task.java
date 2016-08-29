package models;

/**
 * Task class. Holds information about tasks.
 *
 * Created by juan on 29/08/16.
 */
public class Task {

    private String name, info;
    private int sprint, status, prio;

    // CONSTRUCTORS

    public Task (){

    }

    public Task(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public Task(String name, String info, int sprint, int status, int prio) {
        this.name = name;
        this.info = info;
        this.sprint = sprint;
        this.status = status;
        this.prio = prio;
    }

    // GETTERS & SETTERS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getSprint() {
        return sprint;
    }

    public void setSprint(int sprint) {
        this.sprint = sprint;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }
}
