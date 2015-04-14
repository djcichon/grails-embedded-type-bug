
import spock.lang.Specification

@SuppressWarnings("GrMethodMayBeStatic")
class BeforeInsertIntegrationSpec extends Specification {

    void "Value and PersistentValue are different"() {
        when:
        def domain = new BeforeInsertDomain(embeddedExample: new EmbeddedExample(intProp: 1))
        domain.save(flush: true)

        then:
        !domain.embeddedExample.is(domain.getPersistentValue('embeddedExample'))
    }

    void "IsDirty after update"() {
        given:
        Integer originalValue = 1
        Integer updatedValue = 2

        def domain = new BeforeInsertDomain(embeddedExample: new EmbeddedExample(intProp: originalValue))
        domain.save(flush: true)

        when:
        domain.embeddedExample.intProp = updatedValue

        then:
        domain.isDirty()
        domain.isDirty('embeddedExample')
    }

    void "With Criteria"() {
        given:
        Integer originalValue = 1
        Integer updatedValue = 2

        def domain = new BeforeInsertDomain(embeddedExample: new EmbeddedExample(intProp: originalValue))
        domain.save(flush: true)

        when:
        domain.embeddedExample.intProp = updatedValue
        domain.save(flush: true)

        then:
        // Only one exists, and it has the updated value.
        BeforeInsertDomain.list().size() == 1
        BeforeInsertDomain.list().first().embeddedExample.intProp == updatedValue

        // Search on the criteria that was changed
        def result = BeforeInsertDomain.withCriteria {
            eq 'embeddedExample.intProp', originalValue
        }

        // Result should be empty
        result.isEmpty()
    }
}
