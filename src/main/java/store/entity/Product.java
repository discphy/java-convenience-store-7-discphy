package store.entity;

public class Product {

    private Long id;
    private final ProductInfo info;
    private Promotion promotion;
    private ProductStock stock;

    public Product(ProductInfo info, ProductStock stock, Promotion promotion) {
        this.info = info;
        this.stock = stock;
        this.promotion = promotion;
    }

    public Long getId() {
        return id;
    }

    public ProductInfo getInfo() {
        return info;
    }

    public ProductStock getStock() {
        return stock;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStock(ProductStock stock) {
        this.stock = stock;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
