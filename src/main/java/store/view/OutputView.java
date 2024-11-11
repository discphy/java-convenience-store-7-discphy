package store.view;

import store.domain.entity.Product;
import store.common.formatter.OrderResultsFormatter;
import store.common.formatter.ProductFormatter;
import store.order.vo.OrderResults;

import java.util.List;

public class OutputView {

    private static final String HELLO_MESSAGE = "\n안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";

    public void printProducts(List<Product> products) {
        System.out.println(HELLO_MESSAGE);

        products.stream()
                .map(ProductFormatter::format)
                .forEach(System.out::println);
    }

    public void printReceipt(OrderResults orderResults) {
        System.out.println(OrderResultsFormatter.format(orderResults));
    }
}
