package store.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.*;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionSufficientStrategyTest {

    PromotionSufficientStrategy promotionSufficientStrategy;

    @BeforeEach
    void setUp() {
        promotionSufficientStrategy = new PromotionSufficientStrategy();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_같거나_크지_않을_경우_조건_false_반환(OrderCommand command) {
        //when
        boolean condition = promotionSufficientStrategy.condition(command);

        //then
        assertThat(condition).isFalse();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_같거나_클_경우_true_반환(OrderCommand command) {
        //when
        boolean condition = promotionSufficientStrategy.condition(command);

        //then
        assertThat(condition).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void 프로모션_재고_수가_주문_수보다_같거나_클_경우_주문한다_증정을_받음(OrderCommand command,
                                                OrderResultQuantity orderResultQuantity,
                                                ProductStock stock) {
        //when
        OrderResult orderResult = promotionSufficientStrategy.process(command, OrderApprover.builder()
                .freeQuantity((objects) -> true)
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
    void 프로모션_재고_수가_주문_수보다_같거나_클_경우_주문한다_증정을_받지_않음(OrderCommand command,
                                                OrderResultQuantity orderResultQuantity,
                                                ProductStock stock) {
        //when
        OrderResult orderResult = promotionSufficientStrategy.process(command, OrderApprover.builder()
                .freeQuantity((objects) -> false)
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

    static Stream<OrderCommand> 프로모션_재고_수가_주문_수보다_같거나_크지_않을_경우_조건_false_반환() {
        return Stream.of(
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 7),
                        null),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 7),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 10),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.of(2023, 1, 1))))
        );
    }

    static Stream<OrderCommand> 프로모션_재고_수가_주문_수보다_같거나_클_경우_true_반환() {
        return Stream.of(
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 10),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(10,
                        new ProductInfo("콜라", 1_000L),
                        new ProductStock(10, 12),
                        new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))),
                OrderCommand.of(1,
                        new ProductInfo("오렌지주스", 1_800L),
                        new ProductStock(0, 2),
                        new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)))
        );
    }

    static Stream<Arguments> 프로모션_재고_수가_주문_수보다_같거나_클_경우_주문한다_증정을_받지_않음() {
        return Stream.of(
                Arguments.of(
                        OrderCommand.of(11,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 20),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(11, 3),
                        new ProductStock(10, 9)
                ),
                Arguments.of(
                        OrderCommand.of(11,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 12),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(11, 3),
                        new ProductStock(10, 1)
                ),
                Arguments.of(
                        OrderCommand.of(1,
                                new ProductInfo("오렌지주스", 1_800L),
                                new ProductStock(0, 2),
                                new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(1, 0),
                        new ProductStock(0, 1)
                )
        );
    }

    static Stream<Arguments> 프로모션_재고_수가_주문_수보다_같거나_클_경우_주문한다_증정을_받음() {
        return Stream.of(
                Arguments.of(
                        OrderCommand.of(10,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 20),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(10, 3),
                        new ProductStock(10, 10)
                ),
                Arguments.of(
                        OrderCommand.of(11,
                                new ProductInfo("콜라", 1_000L),
                                new ProductStock(10, 12),
                                new Promotion("2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(12, 4),
                        new ProductStock(10, 0)
                ),
                Arguments.of(
                        OrderCommand.of(1,
                                new ProductInfo("오렌지주스", 1_800L),
                                new ProductStock(0, 2),
                                new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX))
                        ),
                        OrderResultQuantity.of(2, 1),
                        new ProductStock(0, 0)
                )
        );
    }
}