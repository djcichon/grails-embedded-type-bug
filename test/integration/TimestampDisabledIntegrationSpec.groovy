import grails.test.spock.IntegrationSpec

@SuppressWarnings("GrMethodMayBeStatic")
class TimestampDisabledIntegrationSpec extends IntegrationSpec {

    void "Value and PersistentValue are different"() {
        when:
        def domain = new TimestampDisabledDomain(embeddedExample: new EmbeddedExample(intProp: 1))
        domain.save(flush: true)

        then:
        !domain.embeddedExample.is(domain.getPersistentValue('embeddedExample'))
    }

    void "IsDirty after update"() {
        given:
        Integer originalValue = 1
        Integer updatedValue = 2

        def domain = new TimestampDisabledDomain(embeddedExample: new EmbeddedExample(intProp: originalValue))
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

        def domain = new TimestampDisabledDomain(embeddedExample: new EmbeddedExample(intProp: originalValue))
        domain.save(flush: true)

        when:
        domain.embeddedExample.intProp = updatedValue
        domain.save(flush: true)

        then:
        // Only one exists, and it has the updated value.
        TimestampDisabledDomain.list().size() == 1
        TimestampDisabledDomain.list().first().embeddedExample.intProp == updatedValue

        // Search on the criteria that was changed
        def result = TimestampDisabledDomain.withCriteria {
            eq 'embeddedExample.intProp', originalValue
        }

        // Result should be empty
        result.isEmpty()
    }
}
