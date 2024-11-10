package store.format;

import store.entity.Product;

public class ProductMessageFormatter implements MessageFormatter<Product> {

    private static final String DEFAULT_FORMAT = "- %s %,d원 %s";
    private static final String STOCK_FORMAT = "%d개";
    private static final String SOLD_OUT = "재고 없음";

    @Override
    public String format(Product product) {
        StringBuilder builder = new StringBuilder();

        if (product.getPromotion() != null) {
            builder.append(promotionMessage(product)).append("\n");
        }

        builder.append(productMessage(product));

        return builder.toString();
    }

    private String promotionMessage(Product product) {
        return productMessage(product, product.getStock().getPromotionStock()) + " " + product.getPromotion().getName();
    }

    private String productMessage(Product product) {
        return productMessage(product, product.getStock().getGeneralStock());
    }

    private String productMessage(Product product, int stock) {
        return String.format(DEFAULT_FORMAT,
                product.getInfo().getName(),
                product.getInfo().getPrice(),
                stockMessage(stock));
    }

    private String stockMessage(int generalStock) {
        if (generalStock > 0) {
            return String.format(STOCK_FORMAT, generalStock);
        }

        return SOLD_OUT;
    }
}
