package store.common.formatter;

import store.domain.entity.Product;

public class ProductFormatter {

    private static final String DEFAULT_FORMAT = "- %s %,d원 %s";
    private static final String STOCK_FORMAT = "%d개";
    private static final String SOLD_OUT = "재고 없음";

    public static String format(Product product) {
        StringBuilder builder = new StringBuilder();

        if (product.getPromotion() != null) {
            builder.append(promotionMessage(product)).append("\n");
        }

        builder.append(productMessage(product));

        return builder.toString();
    }

    private static String promotionMessage(Product product) {
        return productMessage(product, product.getStock().getPromotionStock()) + " " + product.getPromotion().getName();
    }

    private static String productMessage(Product product) {
        return productMessage(product, product.getStock().getGeneralStock());
    }

    private static String productMessage(Product product, int stock) {
        return String.format(DEFAULT_FORMAT,
                product.getInfo().getName(),
                product.getInfo().getPrice(),
                stockMessage(stock));
    }

    private static String stockMessage(int generalStock) {
        if (generalStock > 0) {
            return String.format(STOCK_FORMAT, generalStock);
        }

        return SOLD_OUT;
    }
}
