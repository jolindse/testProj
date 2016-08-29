package models

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by juan on 29/08/16.
 */
class StoryTest extends Specification {

    def "Test to create a story"() {
        when:
            Story story = new Story();
        then:
            story != null;
    }

    @Shared
    Story story = new Story();

    def "Get/set story number"() {
        when:
        story.setNumber(4);
        then:
        story.getNumber() == 4;
    }

    def "Get/set story text"() {
        when:
        story.setText("Hejsan");
        then:
        story.getText() == "Hejsan";
    }

    def "Get/set story info"() {
        when:
        story.setInfo("Hoppsan");
        then:
        story.getInfo() == "Hoppsan";
    }
}
