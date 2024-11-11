package store.stock.resolver;

import org.junit.jupiter.api.Test;
import store.domain.entity.Promotion;
import store.domain.vo.ProductInfo;
import store.domain.vo.ProductStock;
import store.domain.vo.PromotionCount;
import store.domain.vo.PromotionDate;
import store.order.dto.OrderCommand;
import store.stock.strategy.GeneralStockStrategy;
import store.stock.strategy.PromotionInsufficientStrategy;
import store.stock.strategy.PromotionSufficientStrategy;
import store.stock.strategy.StockStrategy;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StockStrategyResolverTest {

    @Test
    void 일반재고_주문_방법() {
        //given
        StockStrategyResolver resolver = new StockStrategyResolver();
        OrderCommand command = OrderCommand.of(1, new ProductInfo("콜라", 1_000L), new ProductStock(1, 10), null);

        //when
        StockStrategy resolve = resolver.resolve(command);
        assertThat(resolve).isInstanceOf(GeneralStockStrategy.class);
    }

    @Test
    void 프로모션_재고_충분_주문_방법() {
        //given
        StockStrategyResolver resolver = new StockStrategyResolver();
        OrderCommand command = OrderCommand.of(1, new ProductInfo("콜라", 1_000L), new ProductStock(1, 10),
                new Promotion("탄산2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)));

        //when
        StockStrategy resolve = resolver.resolve(command);
        assertThat(resolve).isInstanceOf(PromotionSufficientStrategy.class);
    }

    @Test
    void 프로모션_재고_불충분_주문_방법() {
        //given
        StockStrategyResolver resolver = new StockStrategyResolver();
        OrderCommand command = OrderCommand.of(10, new ProductInfo("콜라", 1_000L), new ProductStock(10, 7),
                new Promotion("탄산2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)));

        //when
        StockStrategy resolve = resolver.resolve(command);
        assertThat(resolve).isInstanceOf(PromotionInsufficientStrategy.class);
    }

}