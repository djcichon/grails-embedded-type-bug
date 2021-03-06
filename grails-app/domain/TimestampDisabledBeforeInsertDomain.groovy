
class TimestampDisabledBeforeInsertDomain implements Serializable {
    EmbeddedExample embeddedExample

    Date dateCreated = new Date()          // dateCreated
    Date lastUpdated = new Date()          // dateCreated

    static embedded = ['embeddedExample']

    static mapping = {
        autoTimestamp false
    }

    def beforeInsert() {}
}
