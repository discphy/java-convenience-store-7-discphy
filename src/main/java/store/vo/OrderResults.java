package store.vo;

import java.util.List;

public class OrderResults {

    private final List<OrderResult> orderResults;
    private final OrderMembership orderMembership;

    public OrderResults(List<OrderResult> orderResults) {
        this.orderResults = orderResults;
        this.orderMembership = OrderMembership.ofNotApply();
    }

    public static OrderResults from(List<OrderResult> orderResults) {
        return new OrderResults(orderResults);
    }

    public List<OrderResult> getOrderResults() {
        return List.copyOf(orderResults);
    }

    public OrderMembership getOrderMembership() {
        return orderMembership;
    }

    public boolean hasFreeQuantity() {
        return orderResults.stream().anyMatch(OrderResult::hasFreeQuantity);
    }

    public long totalPrice() {
        return orderResults.stream()
                .mapToLong(OrderResult::getTotalPrice)
                .sum();
    }

    public int totalQuantity() {
        return orderResults.stream()
                .mapToInt(orderResult -> orderResult.getOrderResultQuantity().getTotalQuantity())
                .sum();
    }

    public long promotionDiscountPrice() {
        return orderResults.stream()
                .mapToLong(OrderResult::getPromotionDiscountPrice)
                .sum();
    }

    public long membershipDiscountPrice() {
        return orderMembership.membershipDiscountPrice(orderResults.stream()
                .mapToLong(OrderResult::getMembershipDiscountPrice)
                .sum());
    }
}
