package store.stock.strategy;

import store.order.dto.OrderCommand;
import store.order.dto.OrderApprover;
import store.order.vo.OrderResult;

public interface StockStrategy {

    boolean condition(OrderCommand command);

    OrderResult process(OrderCommand command, OrderApprover approver);

    default int notChangeStock() {
        return 0;
    }
}
