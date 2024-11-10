package store.entity;

import static store.constant.ErrorMessage.INVALID_PRODUCT_PRICE;
import static store.constant.ErrorMessage.NOT_BLANK_PRODUCT_NAME;

public class ProductInfo {

    private final String name;
    private final long price;

    public ProductInfo(String name, long price) {
        validateName(name);
        validatePrice(price);

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_PRODUCT_NAME.message());
        }
    }

    private void validatePrice(long price) {
        if (price <= 0) {
            throw new IllegalArgumentException(INVALID_PRODUCT_PRICE.message());
        }
    }
}
