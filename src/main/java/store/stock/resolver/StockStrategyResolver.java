package store.stock.resolver;

import store.common.constant.StockCondition;
import store.order.dto.OrderCommand;
import store.stock.strategy.GeneralStockStrategy;
import store.stock.strategy.PromotionInsufficientStrategy;
import store.stock.strategy.PromotionSufficientStrategy;
import store.stock.strategy.StockStrategy;

import java.util.EnumMap;
import java.util.Map;

import static store.common.constant.ErrorMessage.INVALID_STOCK;
import static store.common.constant.StockCondition.*;

public class StockStrategyResolver {

    private final Map<StockCondition, StockStrategy> strategyMap = new EnumMap<>(StockCondition.class);

    public StockStrategyResolver() {
        strategyMap.put(GENERAL_STOCK, new GeneralStockStrategy());
        strategyMap.put(PROMOTION_SUFFICIENT, new PromotionSufficientStrategy());
        strategyMap.put(PROMOTION_INSUFFICIENT, new PromotionInsufficientStrategy());
    }

    public StockStrategy resolve(OrderCommand command) {
        return strategyMap.values().stream()
                .filter(strategy -> strategy.condition(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_STOCK.message()));
    }
}
