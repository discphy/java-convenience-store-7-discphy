package store.view;

import store.entity.Product;
import store.format.MessageFormatter;
import store.vo.OrderResults;

import java.util.List;

public class OutputView {

    private final MessageFormatter<Product> productMessageFormatter;
    private final MessageFormatter<OrderResults> orderResultsMessageFormatter;

    public OutputView(MessageFormatter<Product> productMessageFormatter,
                      MessageFormatter<OrderResults> orderResultsMessageFormatter) {
        this.productMessageFormatter = productMessageFormatter;
        this.orderResultsMessageFormatter = orderResultsMessageFormatter;
    }

    public void printProducts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");

        products.stream()
                .map(productMessageFormatter::format)
                .forEach(System.out::println);
    }

    public void printReceipt(OrderResults orderResults) {
        System.out.println(orderResultsMessageFormatter.format(orderResults));
    }
}
