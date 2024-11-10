package store.domain.dto;

import store.domain.entity.Product;
import store.domain.vo.ProductInfo;
import store.domain.vo.ProductStock;
import store.domain.entity.Promotion;

import java.util.Map;

import static store.common.utils.MapUtils.getInt;
import static store.common.utils.MapUtils.getLong;

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
        return new ProductInitDto(product.get("name"),
                getLong(product, "price"),
                getInt(product, "quantity"),
                product.get("promotion"));
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
