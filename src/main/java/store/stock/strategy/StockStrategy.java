package store.stock.strategy;

import store.order.dto.OrderCommand;
import store.order.dto.OrderFunction;
import store.order.vo.OrderResult;

public interface StockStrategy {

    boolean condition(OrderCommand command);

    OrderResult process(OrderCommand command, OrderFunction function);

    default int notChangeStock() {
        return 0;
    }
}
