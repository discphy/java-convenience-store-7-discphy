package store.order.vo;

public class OrderResultQuantity {

    private final int totalQuantity;
    private final int freeQuantity;

    private OrderResultQuantity(int totalQuantity, int freeQuantity) {
        this.totalQuantity = totalQuantity;
        this.freeQuantity = freeQuantity;
    }

    public static OrderResultQuantity of(int totalQuantity, int freeQuantity) {
        return new OrderResultQuantity(totalQuantity, freeQuantity);
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }
}
