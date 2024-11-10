package store.format;

import store.vo.OrderResult;
import store.vo.OrderResults;

public class OrderResultsMessageFormatter implements MessageFormatter<OrderResults> {

    private static final String START_RECEIPT_DELIMITER = "\n=============W 편의점=============\n";
    private static final String FREE_QUANTITY_DELIMITER = "=============증    정=============\n";
    private static final String FINAL_RECEIPT_DELIMITER = "=================================\n";
    private static final String NAME_FORMAT = "%-15s";
    private static final String QUANTITY_FORMAT = "%5s";
    private static final String PRICE_FORMAT = "%10s";
    private static final String ORDER_DEFAULT_FORMAT = NAME_FORMAT + QUANTITY_FORMAT + PRICE_FORMAT + "%n";

    @Override
    public String format(OrderResults orderResults) {
        StringBuilder builder = new StringBuilder();

        appendOrderSection(orderResults, builder);
        appendFreeQuantitySection(orderResults, builder);
        appendFinalSection(orderResults, builder);

        return builder.toString();
    }

    private void appendOrderSection(OrderResults orderResults, StringBuilder builder) {
        builder.append(START_RECEIPT_DELIMITER);
        builder.append(formatRow("상품명", "수량", "금액"));
        orderResults.getOrderResults().forEach(orderResult -> builder.append(formatOrderResult(orderResult)));
    }

    private void appendFreeQuantitySection(OrderResults orderResults, StringBuilder builder) {
        if (orderResults.hasFreeQuantity()) {
            builder.append(FREE_QUANTITY_DELIMITER);
            orderResults.getOrderResults().stream()
                    .filter(OrderResult::hasFreeQuantity)
                    .forEach(result -> builder.append(formatFreeQuantity(result)));
        }
    }

    private void appendFinalSection(OrderResults orderResults, StringBuilder builder) {
        builder.append(FINAL_RECEIPT_DELIMITER);
        finalMessage(builder, orderResults);
    }

    private String formatOrderResult(OrderResult orderResult) {
        return formatRow(orderResult.getProductInfo().getName(),
                orderResult.getOrderResultQuantity().getTotalQuantity(),
                orderResult.getTotalPrice());
    }

    private String formatFreeQuantity(OrderResult orderResult) {
        return formatRow(orderResult.getProductInfo().getName(),
                formatNumber(orderResult.getOrderResultQuantity().getFreeQuantity()),
                "");
    }

    private void finalMessage(StringBuilder builder, OrderResults orderResults) {
        long totalPrice = orderResults.totalPrice();
        long promotionDiscountPrice = orderResults.promotionDiscountPrice();
        long membershipDiscountPrice = orderResults.membershipDiscountPrice();
        long payment = totalPrice - promotionDiscountPrice - membershipDiscountPrice;

        builder.append(formatRow("총구매액", orderResults.totalQuantity(), totalPrice));
        builder.append(formatRow("행사할인", "", formatNumber(-promotionDiscountPrice)));
        builder.append(formatRow("멤버십할인", "", formatNumber(-membershipDiscountPrice)));
        builder.append(formatRow("내실돈", "", formatNumber(payment)));
    }

    private String formatRow(String name, int quantity, long price) {
        return formatRow(name, formatNumber(quantity), formatNumber(price));
    }

    private String formatRow(String name, String quantity, String price) {
        return String.format(ORDER_DEFAULT_FORMAT, formatName(name), formatQuantity(quantity), formatPrice(price));
    }

    private String formatName(String name) {
        return String.format(NAME_FORMAT, name);
    }

    private String formatQuantity(String quantity) {
        return String.format(QUANTITY_FORMAT, quantity);
    }

    private String formatPrice(String price) {
        return String.format(PRICE_FORMAT, price);
    }

    private String formatNumber(long number) {
        return String.format("%,d", number);
    }

    private String formatNumber(int number) {
        return String.format("%,d", number);
    }
}
