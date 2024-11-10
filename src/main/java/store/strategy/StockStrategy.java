package store.strategy;

import store.dto.OrderCommand;
import store.dto.OrderApprover;
import store.vo.OrderResult;

public interface StockStrategy {

    boolean condition(OrderCommand command);

    OrderResult process(OrderCommand command, OrderApprover approver);

}
