package store.stock.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.entity.Promotion;
import store.domain.vo.ProductInfo;
import store.domain.vo.ProductStock;
import store.domain.vo.PromotionCount;
import store.domain.vo.PromotionDate;
import store.order.dto.OrderApprover;
import store.order.dto.OrderCommand;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralStockStrategyTest {

    GeneralStockStrategy generalStockStrategy;

    @BeforeEach
    void setUp() {
        generalStockStrategy = new GeneralStockStrategy();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션이_유효하지_않거나_재고_수가_0일때_true_반환(OrderCommand command) {
        //when
        boolean condition = generalStockStrategy.condition(command);

        //then
        assertThat(condition).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션이_유효하지_않거나_재고_수가_0일때_주문한다(OrderCommand command,
                                                OrderResultQuantity orderResultQuantity,
                                                ProductStock stock) {
        //when
        boolean condition = generalStockStrategy.condition(command);
        OrderResult orderResult = generalStockStrategy.process(command, OrderApprover.builder().build());

        //then
        assertThat(condition).isTrue();
        assertThat(orderResult.getOrderResultQuantity().getFreeQuantity())
                .isEqualTo(orderResultQuantity.getFreeQuantity());
        assertThat(orderResult.getOrderResultQuantity().getTotalQuantity())
                .isEqualTo(orderResultQuantity.getTotalQuantity());
        assertThat(orderResult.getUpdateStock().getGeneralStock())
                .isEqualTo(stock.getGeneralStock());
        assertThat(orderResult.getUpdateStock().getPromotionStock())
                .isEqualTo(stock.getPromotionStock());
    }

    static Stream<OrderCommand> 프로모션이_유효하지_않거나_재고_수가_0일때_true_반환() {
        return Stream.of(
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 7),
                        null),
                OrderCommand.of(5,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 0),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 10),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.of(2023, 1, 1))))
        );
    }

    static Stream<Arguments> 프로모션이_유효하지_않거나_재고_수가_0일때_주문한다() {
        return Stream.of(
                Arguments.of(
                        OrderCommand.of(10,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 7),
                                null
                        ),
                        OrderResultQuantity.of(10, 0),
                        new ProductStock(7, 0)
                ),
                Arguments.of(
                        OrderCommand.of(7,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 0),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(7, 0),
                        new ProductStock(3, 0)
                ),
                Arguments.of(
                        OrderCommand.of(1,
                                new ProductInfo("오렌지주스", 1_800L),
                                new ProductStock(1, 12),
                                new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.of(2023, 1, 1)))
                        ),
                        OrderResultQuantity.of(1, 0),
                        new ProductStock(1, 11)
                )
        );
    }
}