package by.project.dartlen.proofofconcept.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {
    private String url;
    private String name;
    private Float price;
    private String description;

    public Product(){}

    public Product(String url, String name, Float price, String description){
        this.url   = url;
        this.name  = name;
        this.price = price;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
