package store.dto;

import store.entity.Product;
import store.entity.ProductInfo;
import store.entity.ProductStock;
import store.entity.Promotion;

import java.util.Map;

import static store.utils.MapUtils.getInt;
import static store.utils.MapUtils.getLong;

public class ProductInitDto {

    private final String name;
    private final long price;
    private final int quantity;
    private final String promotionName;

    private ProductInitDto(String name, long price, int quantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static ProductInitDto from(Map<String, String> product) {
        String name = product.get("name");
        long price = getLong(product, "price");
        int quantity = getInt(product, "quantity");
        String promotion = product.get("promotion");

        return new ProductInitDto(name, price, quantity, promotion);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public Product toEntity(ProductStock stock, Promotion promotion) {
        return new Product(new ProductInfo(name, price), stock, promotion);
    }
}