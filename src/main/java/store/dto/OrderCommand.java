package store.dto;

import store.entity.ProductInfo;
import store.entity.ProductStock;
import store.entity.Promotion;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

public class OrderCommand {

    private final int quantity;
    private final ProductInfo info;
    private final ProductStock stock;
    private final Promotion promotion;

    public OrderCommand(int quantity, ProductInfo info, ProductStock stock, Promotion promotion) {
        this.quantity = quantity;
        this.info = info;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static OrderCommand of(int quantity, ProductInfo info, ProductStock stock, Promotion promotion) {
        return new OrderCommand(quantity, info, stock, promotion);
    }

    public int getQuantity() {
        return quantity;
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

    public OrderResult toResult(OrderResultQuantity orderResultQuantity, ProductStock updateStock) {
        return OrderResult.of(info, orderResultQuantity, updateStock);
    }
}
