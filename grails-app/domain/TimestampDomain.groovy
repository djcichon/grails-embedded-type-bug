

class TimestampDomain {
    EmbeddedExample embeddedExample

    Date dateCreated
    Date lastUpdated

    static embedded = ['embeddedExample']
}
