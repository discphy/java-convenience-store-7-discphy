package store.format;

import store.vo.OrderResult;
import store.vo.OrderResults;

public class OrderResultsMessageFormatter implements MessageFormatter<OrderResults> {

    private static final String START_RECEIPT_DELIMITER = "=============W 편의점=============";
    private static final String FREE_QUANTITY_DELIMITER = "=============증    정=============";
    private static final String FINAL_RECEIPT_DELIMITER = "=================================";
    private static final String NAME_FORMAT = "%-15s";
    private static final String QUANTITY_FORMAT = "%5s";
    private static final String PRICE_FORMAT = "%10s";
    private static final String ORDER_DEFAULT_FORMAT = NAME_FORMAT + QUANTITY_FORMAT + PRICE_FORMAT + "%n";

    @Override
    public String format(OrderResults orderResults) {
        StringBuilder builder = new StringBuilder();

        orderMessage(orderResults, builder);
        freeQuantityMessage(orderResults, builder);
        finalMessage(orderResults, builder);

        return builder.toString();
    }

    private void finalMessage(OrderResults orderResults, StringBuilder builder) {
        builder.append(FINAL_RECEIPT_DELIMITER).append("\n");
        finalMessage(builder, orderResults);
    }

    private void freeQuantityMessage(OrderResults orderResults, StringBuilder builder) {
        if (hasFreeQuantity(orderResults)) {
            builder.append(FREE_QUANTITY_DELIMITER).append("\n");
            freeQuantityMessage(builder, orderResults);
        }
    }

    private void orderMessage(OrderResults orderResults, StringBuilder builder) {
        builder.append(START_RECEIPT_DELIMITER).append("\n");
        orderResultsMessage(builder, orderResults);
    }

    private void orderResultsMessage(StringBuilder builder, OrderResults orderResults) {
        builder.append(format("상품명", "수량", "금액"));
        orderResults.getOrderResults().stream()
                .map(this::orderResultMessage)
                .forEach(builder::append);
    }

    private String orderResultMessage(OrderResult orderResult) {
        return format(orderResult.getProductInfo().getName(),
                orderResult.getOrderResultQuantity().getTotalQuantity(),
                orderResult.getTotalPrice());
    }

    private boolean hasFreeQuantity(OrderResults orderResults) {
        return orderResults.getOrderResults().stream()
                .anyMatch(this::hasFreeQuantity);
    }

    private boolean hasFreeQuantity(OrderResult orderResult) {
        return orderResult.getOrderResultQuantity().getFreeQuantity() > 0;
    }

    private void freeQuantityMessage(StringBuilder builder, OrderResults orderResults) {
        orderResults.getOrderResults().stream()
                .filter(this::hasFreeQuantity)
                .map(this::freeQuantityMessage)
                .forEach(builder::append);
    }

    private String freeQuantityMessage(OrderResult orderResult) {
        return nameQuantityFormat(orderResult.getProductInfo().getName(), orderResult.getOrderResultQuantity().getFreeQuantity());
    }

    private void finalMessage(StringBuilder builder, OrderResults orderResults) {
        long totalPrice = orderResults.totalPrice();
        long promotionDiscountPrice = orderResults.promotionDiscountPrice();
        long membershipDiscountPrice = orderResults.membershipDiscountPrice();
        long payment = totalPrice - promotionDiscountPrice - membershipDiscountPrice;

        builder.append(format("총구매액", orderResults.totalQuantity(), totalPrice));
        builder.append(namePriceFormat("행사할인", -promotionDiscountPrice));
        builder.append(namePriceFormat("멤버십할인", -membershipDiscountPrice));
        builder.append(namePriceFormat("내실돈", payment));
    }

    private String nameQuantityFormat(String name, long quantity) {
        return format(name, number(quantity), "");
    }

    private String namePriceFormat(String name, long price) {
        return format(name, "", number(price));
    }

    private String format(String name, int quantity, long price) {
        return format(name, number(quantity), number(price));
    }

    private String format(String name, String quantity, String price) {
        return String.format(ORDER_DEFAULT_FORMAT, name(name), quantity(quantity), price(price));
    }

    private String name(String name) {
        return String.format(NAME_FORMAT, name);
    }

    private String quantity(String quantity) {
        return String.format(QUANTITY_FORMAT, quantity);
    }

    private String price(String price) {
        return String.format(PRICE_FORMAT, price);
    }

    private String number(long number) {
        return String.format("%,d", number);
    }

    private String number(int number) {
        return String.format("%,d", number);
    }
}
