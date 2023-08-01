package eg.kafka.template.domain;

public class ArticleSource {
    private String system;
    private String systemId;

    public ArticleSource() {
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
