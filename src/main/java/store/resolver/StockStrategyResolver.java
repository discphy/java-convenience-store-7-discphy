package store.resolver;

import store.constant.StockStrategyCondition;
import store.dto.OrderCommand;
import store.strategy.GeneralStockStrategy;
import store.strategy.PromotionInsufficientStrategy;
import store.strategy.PromotionSufficientStrategy;
import store.strategy.StockStrategy;

import java.util.EnumMap;
import java.util.Map;

import static store.constant.ErrorMessage.INVALID_STOCK;
import static store.constant.StockStrategyCondition.*;

public class StockStrategyResolver {

    private final Map<StockStrategyCondition, StockStrategy> strategyMap = new EnumMap<>(StockStrategyCondition.class);

    public StockStrategyResolver() {
        strategyMap.put(NO_PROMOTION, new GeneralStockStrategy());
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
