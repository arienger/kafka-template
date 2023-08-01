package eg.kafka.template.domain;

public class ArticleEnvelope {
    private String clientId;
    private GenericArticle genericId;

    public ArticleEnvelope() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public GenericArticle getGenericId() {
        return genericId;
    }

    public void setGenericId(GenericArticle genericId) {
        this.genericId = genericId;
    }
}
