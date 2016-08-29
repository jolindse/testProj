package models

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * <h1>Created by Mattias on 2016-08-29.</h1>
 */
class IndividualTest extends Specification {

    def "Testing cretion of new indiidual"() {
        when: "I instanciate a new Individual"
            Individual individual = new Individual()
        then: "individual is not null"
            individual != null
    }

    @Unroll
    def "Testing full constructor"() {
        when:
            Individual ind = new Individual(firstName, lastName, info)
        then:
            ind.getFirstName() == expFirst
            ind.getLastName() == expLast
            ind.getInfo() == expInfo
        where:
            firstName|lastName|info|expFirst|expLast|expInfo
            'Mattias'|'Larsson'|'info'|'Mattias'|'Larsson'|'info'

    }
    @Shared
        Individual individual = new Individual()

    def "Testing set frstname of individual"() {
        when: "I set firstName of individual"
            individual.setFirstName("Mattias")
        then: "FisrtName is not null"
            individual.getFirstName() == "Mattias"
    }

    def "Testing set llastname of individual"() {
        when:
            individual.setLastName("Larsson")
        then:
            individual.getLastName() == "Larsson"
    }

    def "Testing setInfo"() {
        when:
            individual.setInfo("info")
        then:
            individual.getInfo() == "info"
    }
}
