package eg.kafka.template.domain;

import java.util.List;

public class GenericArticle {
    private String id;
    private String name;
    private List<Article> products;

    public GenericArticle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getProducts() {
        return products;
    }

    public void setProducts(List<Article> products) {
        this.products = products;
    }
}
