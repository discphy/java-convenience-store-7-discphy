package store.config;

import store.domain.repository.ProductRepository;
import store.domain.repository.PromotionRepository;
import store.order.controller.OrderController;
import store.order.service.OrderService;
import store.stock.resolver.StockStrategyResolver;
import store.view.InputView;
import store.view.OutputView;

public class ApplicationConfig {

    private InputView inputView;
    private OutputView outputView;
    private OrderService orderService;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;
    private StockStrategyResolver stockStrategyResolver;

    public OrderController orderController() {
        return new OrderController(inputView(), outputView(), orderService());
    }

    public DataInitializer dataInitializer() {
        return new DataInitializer(productRepository(), promotionRepository());
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }

        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }

        return outputView;
    }

    private OrderService orderService() {
        if (orderService == null) {
            orderService = new OrderService(productRepository(), stockStrategyResolver());
        }

        return orderService;
    }

    private StockStrategyResolver stockStrategyResolver() {
        if (stockStrategyResolver == null) {
            stockStrategyResolver = new StockStrategyResolver();
        }

        return stockStrategyResolver;
    }

    private ProductRepository productRepository() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }

        return productRepository;
    }

    private PromotionRepository promotionRepository() {
        if (promotionRepository == null) {
            promotionRepository = new PromotionRepository();
        }

        return promotionRepository;
    }
}
