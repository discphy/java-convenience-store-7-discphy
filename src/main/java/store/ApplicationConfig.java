package store;

import store.controller.OrderController;
import store.format.OrderResultsMessageFormatter;
import store.format.ProductMessageFormatter;
import store.initializer.DataInitializer;
import store.parser.OrderItemsInputParser;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.resolver.StockStrategyResolver;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class ApplicationConfig {

    public OrderController orderController() {
        return new OrderController(new InputView(),
                outputView(),
                orderService(),
                dataInitializer(),
                new OrderItemsInputParser());
    }

    private OutputView outputView() {
        return new OutputView(new ProductMessageFormatter(), new OrderResultsMessageFormatter());
    }

    private OrderService orderService() {
        return new OrderService(productRepository(), new StockStrategyResolver());
    }

    private DataInitializer dataInitializer() {
        return new DataInitializer(productRepository(), new PromotionRepository());
    }

    private ProductRepository productRepository() {
        return new ProductRepository();
    }
}
