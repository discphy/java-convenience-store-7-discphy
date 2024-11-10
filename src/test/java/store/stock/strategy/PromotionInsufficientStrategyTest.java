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
import store.order.dto.OrderFunction;
import store.order.dto.OrderCommand;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionInsufficientStrategyTest {

    PromotionInsufficientStrategy promotionInsufficientStrategy;

    @BeforeEach
    void setUp() {
        promotionInsufficientStrategy = new PromotionInsufficientStrategy();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_작지_않을_경우_조건_false_반환(OrderCommand command) {
        //when
        boolean condition = promotionInsufficientStrategy.condition(command);

        //then
        assertThat(condition).isFalse();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_작을_경우_true_반환(OrderCommand command) {
        //when
        boolean condition = promotionInsufficientStrategy.condition(command);

        //then
        assertThat(condition).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_작을_경우_주문한다_정가_결제_진행(OrderCommand command,
                                                OrderResultQuantity orderResultQuantity,
                                                ProductStock stock) {
        //when
        OrderResult orderResult = promotionInsufficientStrategy.process(command, OrderFunction.builder()
                .fullPayment((objects) -> true)
                .build());

        //then
        assertThat(orderResult.getOrderResultQuantity().getFreeQuantity())
                .isEqualTo(orderResultQuantity.getFreeQuantity());
        assertThat(orderResult.getOrderResultQuantity().getTotalQuantity())
                .isEqualTo(orderResultQuantity.getTotalQuantity());
        assertThat(orderResult.getUpdateStock().getGeneralStock())
                .isEqualTo(stock.getGeneralStock());
        assertThat(orderResult.getUpdateStock().getPromotionStock())
                .isEqualTo(stock.getPromotionStock());
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_작을_경우_주문한다_정가_결제_진행안함(OrderCommand command,
                                                OrderResultQuantity orderResultQuantity,
                                                ProductStock stock) {
        //when
        OrderResult orderResult = promotionInsufficientStrategy.process(command, OrderFunction.builder()
                .fullPayment((objects) -> false)
                .build());

        //then
        assertThat(orderResult.getOrderResultQuantity().getFreeQuantity())
                .isEqualTo(orderResultQuantity.getFreeQuantity());
        assertThat(orderResult.getOrderResultQuantity().getTotalQuantity())
                .isEqualTo(orderResultQuantity.getTotalQuantity());
        assertThat(orderResult.getUpdateStock().getGeneralStock())
                .isEqualTo(stock.getGeneralStock());
        assertThat(orderResult.getUpdateStock().getPromotionStock())
                .isEqualTo(stock.getPromotionStock());
    }

    static Stream<OrderCommand> 프로모션_재고_수가_주문_수보다_작지_않을_경우_조건_false_반환() {
        return Stream.of(
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 7),
                        null),
                OrderCommand.of(5,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 7),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 10),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.of(2023, 1, 1))))
        );
    }

    static Stream<OrderCommand> 프로모션_재고_수가_주문_수보다_작을_경우_true_반환() {
        return Stream.of(
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 9),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 3),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(1,
                        new ProductInfo("오렌지주스", 1_800L),
                        new ProductStock(0, 0),
                        new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)))
        );
    }

    static Stream<Arguments> 프로모션_재고_수가_주문_수보다_작을_경우_주문한다_정가_결제_진행() {
        return Stream.of(
                Arguments.of(
                        OrderCommand.of(10,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 7),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(10, 2),
                        new ProductStock(7, 0)

                ),
                Arguments.of(
                        OrderCommand.of(11,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 6),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(11, 2),
                        new ProductStock(5, 0)
                ),
                Arguments.of(
                        OrderCommand.of(1,
                                new ProductInfo("오렌지주스", 1_800L),
                                new ProductStock(1, 0),
                                new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(1, 0),
                        new ProductStock(0, 0)
                )
        );
    }

    static Stream<Arguments> 프로모션_재고_수가_주문_수보다_작을_경우_주문한다_정가_결제_진행안함() {
        return Stream.of(
                Arguments.of(
                        OrderCommand.of(10,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 7),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(6, 2),
                        new ProductStock(10, 1)
                ),
                Arguments.of(
                        OrderCommand.of(11,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 6),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(6, 2),
                        new ProductStock(10, 0)
                ),
                Arguments.of(
                        OrderCommand.of(1,
                                new ProductInfo("오렌지주스", 1_800L),
                                new ProductStock(1, 0),
                                new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(0, 0),
                        new ProductStock(1, 0)
                )
        );
    }

}