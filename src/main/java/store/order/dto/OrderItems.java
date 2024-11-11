package store.order.dto;

import java.util.List;

public class OrderItems {

    private final List<OrderItem> orderItems;

    private OrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static OrderItems from(List<OrderItem> orderItems) {
        return new OrderItems(orderItems);
    }

    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
    }
}
