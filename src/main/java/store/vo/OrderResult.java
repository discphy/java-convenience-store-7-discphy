package store.vo;

import store.entity.ProductInfo;
import store.entity.ProductStock;

public class OrderResult {

    private final ProductInfo productInfo;
    private final OrderResultQuantity orderResultQuantity;
    private final ProductStock updateStock;

    public OrderResult(ProductInfo productInfo, OrderResultQuantity orderResultQuantity, ProductStock updateStock) {
        this.productInfo = productInfo;
        this.orderResultQuantity = orderResultQuantity;
        this.updateStock = updateStock;
    }

    public static OrderResult of(ProductInfo productInfo, OrderResultQuantity orderResultQuantity, ProductStock updateStock) {
        return new OrderResult(productInfo, orderResultQuantity, updateStock);
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public OrderResultQuantity getOrderResultQuantity() {
        return orderResultQuantity;
    }

    public ProductStock getUpdateStock() {
        return updateStock;
    }

    public long getTotalPrice() {
        return productInfo.getPrice() * orderResultQuantity.getTotalQuantity();
    }

    public long getPromotionDiscountPrice() {
        return productInfo.getPrice() * orderResultQuantity.getFreeQuantity();
    }

    public long getMembershipDiscountPrice() {
        if (orderResultQuantity.getFreeQuantity() > 0) {
            return 0;
        }

        return productInfo.getPrice() * orderResultQuantity.getTotalQuantity();
    }

    public boolean hasFreeQuantity() {
        return orderResultQuantity.getFreeQuantity() > 0;
    }
}
