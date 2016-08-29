package models

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by juan on 29/08/16.
 */
class TaskTest extends Specification {
    def "Testing creation task"(){
        when:
            Task task = new Task();
        then:
            task != null;
    }

    def "Testing creation name/info"() {
        when:
            Task task = new Task("Namn","Info");
        then:
            task != null;
            task.getName() == "Namn";
            task.getInfo() == "Info";

    }

    def "Testing creation full"() {
        when:
        Task task = new Task("Namn","Info", 1,2,3);
        then:
        task != null;
        task.getName() == "Namn";
        task.getInfo() == "Info";
        task.getSprint() == 1;
        task.getStatus() == 2;
        task.getPrio() == 3;

    }


    @Shared Task task = new Task();


    def "Testing name set"() {
        def name = "Name";
        when:
        task.setName(name);
        then:
        task.getName() == name;
    }

    def "Testing info set"() {
        def info = "Info";
        when:
        task.setInfo(info);
        then:
        task.getInfo() == info;

    }

    def "Testing sprint set"() {
        def sprint = 1;
        when:
        task.setSprint(sprint);
        then:
        task.getSprint() == sprint;

    }

    def "Testing status set"() {
        def status = 1;
        when:
        task.setStatus(status);
        then:
        task.getStatus() == status;

    }

    def "Testing prio set"() {
        def prio = 1;
        when:
        task.setPrio(prio);
        then:
        task.getPrio() == prio;
    }

}
