package store.order.service;

import store.order.dto.OrderFunction;
import store.order.dto.OrderCommand;
import store.order.dto.OrderItem;
import store.order.dto.OrderItems;
import store.domain.entity.Product;
import store.domain.vo.ProductStock;
import store.domain.repository.ProductRepository;
import store.stock.resolver.StockStrategyResolver;
import store.order.vo.OrderResult;
import store.order.vo.OrderResults;

import java.util.List;

import static store.common.constant.ErrorMessage.INVALID_INPUT_NOT_EXIST_PRODUCT;
import static store.common.constant.ErrorMessage.INVALID_INPUT_EXCEED_STOCK;

public class OrderService {

    private final ProductRepository productRepository;
    private final StockStrategyResolver stockStrategyResolver;

    public OrderService(ProductRepository productRepository, StockStrategyResolver stockStrategyResolver) {
        this.productRepository = productRepository;
        this.stockStrategyResolver = stockStrategyResolver;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void validate(OrderItems items) {
        items.getOrderItems().forEach(this::validate);
    }

    public OrderResults order(OrderItems items, OrderFunction function) {
        return OrderResults.from(items.getOrderItems().stream()
                .map(item -> order(item, function))
                .toList());
    }

    private OrderResult order(OrderItem item, OrderFunction function) {
        Product product = getProduct(item);
        OrderCommand command = item.toCommand(product);

        OrderResult result = stockStrategyResolver.resolve(command).process(command, function);

        productRepository.updateStock(product.getId(), result.getUpdateStock());

        return result;
    }

    private void validate(OrderItem orderItem) {
        Product product = getProduct(orderItem);

        if (hasNotSufficientStock(orderItem.getQuantity(), product.getStock())) {
            throw new IllegalArgumentException(INVALID_INPUT_EXCEED_STOCK.message());
        }
    }

    private Product getProduct(OrderItem orderItem) {
        return productRepository.findByName(orderItem.getProductName())
                .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT_NOT_EXIST_PRODUCT.message()));
    }

    private boolean hasNotSufficientStock(int quantity, ProductStock stock) {
        return quantity > stock.getTotalStock();
    }
}
