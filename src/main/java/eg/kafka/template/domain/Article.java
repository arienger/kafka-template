package eg.kafka.template.domain;

public class Article {
    private String itemId;
    private ArticleSource source;

    public Article() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ArticleSource getSource() {
        return source;
    }

    public void setSource(ArticleSource source) {
        this.source = source;
    }
}
