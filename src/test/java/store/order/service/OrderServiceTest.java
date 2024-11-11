package store.order.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.order.dto.OrderFunction;
import store.order.dto.OrderItem;
import store.order.dto.OrderItems;
import store.domain.entity.Product;
import store.config.DataInitializer;
import store.domain.repository.ProductRepository;
import store.domain.repository.PromotionRepository;
import store.stock.resolver.StockStrategyResolver;
import store.order.vo.OrderResults;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_INPUT_NOT_EXIST_PRODUCT;
import static store.common.constant.ErrorMessage.INVALID_INPUT_EXCEED_STOCK;

class OrderServiceTest {

    static ProductRepository productRepository;
    static PromotionRepository promotionRepository;
    StockStrategyResolver stockStrategyResolver;
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        stockStrategyResolver = new StockStrategyResolver();
        orderService = new OrderService(productRepository, stockStrategyResolver);
    }

    @BeforeAll
    static void setUp() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();

        new DataInitializer(productRepository, promotionRepository).init();
    }

    @Test
    void 상품_재고_전체_가져오기() {
        //when
        List<Product> products = orderService.getProducts();

        //then
        assertThat(products).hasSizeGreaterThan(1);
    }

    @Test
    void 주문_상품_검증_없는_상품_입력_시_예외_발생() {
        //given
        OrderItem orderItem = OrderItem.of("없는상품", 10);
        OrderItems orderItems = OrderItems.from(List.of(orderItem));

        //when
        assertThatThrownBy(() -> orderService.validate(orderItems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_NOT_EXIST_PRODUCT.message());
    }

    @Test
    void 주문_상품_검증_재고수_부족_시_예외_발생() {
        //given
        OrderItem orderItem = OrderItem.of("콜라", Integer.MAX_VALUE);
        OrderItems orderItems = OrderItems.from(List.of(orderItem));

        //when
        assertThatThrownBy(() -> orderService.validate(orderItems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_EXCEED_STOCK.message());
    }

    @Test
    void 주문_실행() {
        //given
        OrderItem orderItem = OrderItem.of("콜라", 10);
        OrderItems orderItems = OrderItems.from(List.of(orderItem));

        //when
        OrderResults orderResults = orderService.order(orderItems, OrderFunction.builder()
                .freeQuantity(objects -> true)
                .fullPayment(objects -> true)
                .build());

        //then
        assertThat(orderResults.getOrderResults())
                .extracting(m -> m.getOrderResultQuantity().getTotalQuantity()).containsExactly(10);
    }
}