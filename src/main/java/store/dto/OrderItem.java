package store.dto;

import store.entity.Product;

public class OrderItem {

    private final String productName;
    private final int quantity;

    public OrderItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public static OrderItem of(String productName, int quantity) {
        return new OrderItem(productName, quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderCommand toCommand(Product product) {
        return OrderCommand.of(quantity, product.getInfo(), product.getStock(), product.getPromotion());
    }
}
