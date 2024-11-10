package store.service;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.dto.OrderItem;
import store.dto.OrderItems;
import store.entity.Product;
import store.entity.ProductStock;
import store.repository.ProductRepository;
import store.resolver.StockStrategyResolver;
import store.vo.OrderResult;
import store.vo.OrderResults;

import java.util.List;

import static store.constant.ErrorMessage.INVALID_INPUT_NOT_EXIST_PRODUCT;
import static store.constant.ErrorMessage.INVALID_INPUT_ORDER_QUANTITY;

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

    public OrderResults order(OrderItems items, OrderApprover approver) {
        return OrderResults.from(items.getOrderItems().stream()
                .map(item -> order(item, approver))
                .toList());
    }

    private OrderResult order(OrderItem item, OrderApprover approver) {
        Product product = getProduct(item);
        OrderCommand command = item.toCommand(product);

        OrderResult result = stockStrategyResolver.resolve(command).process(command, approver);

        productRepository.updateStock(product.getId(), result.getUpdateStock());

        return result;
    }

    private void validate(OrderItem orderItem) {
        Product product = getProduct(orderItem);

        if (hasNotSufficientStock(orderItem.getQuantity(), product.getStock())) {
            throw new IllegalArgumentException(INVALID_INPUT_ORDER_QUANTITY.message());
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
