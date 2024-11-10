package store.stock.strategy;

import store.order.dto.OrderApprover;
import store.order.dto.OrderCommand;
import store.domain.vo.PromotionCount;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;

public class PromotionSufficientStrategy implements StockStrategy {

    @Override
    public boolean condition(OrderCommand command) {
        return hasValidPromotion(command) && hasSufficientPromotionStock(command);
    }

    @Override
    public OrderResult process(OrderCommand command, OrderApprover approver) {
        int totalQuantity = command.getQuantity();
        int freeQuantity = calculateFreeQuantity(command);

        if (isAddFreeQuantity(command) && isApprovedForFreeQuantity(command, approver)) {
            freeQuantity++;
            totalQuantity++;
        }

        return createOrderResult(command, totalQuantity, freeQuantity);
    }

    private boolean hasValidPromotion(OrderCommand command) {
        return command.getPromotion() != null && command.getPromotion().isValid();
    }

    private boolean hasSufficientPromotionStock(OrderCommand command) {
        return command.getStock().getPromotionStock() >= command.getQuantity();
    }

    private int calculateFreeQuantity(OrderCommand command) {
        PromotionCount count = command.getPromotion().getCount();
        return command.getQuantity() / count.totalCount();
    }

    private boolean isAddFreeQuantity(OrderCommand command) {
        return isBuyCountMatch(command) && hasStockForFreeQuantity(command);
    }

    private boolean isBuyCountMatch(OrderCommand command) {
        PromotionCount count = command.getPromotion().getCount();
        return count.buyCount() == command.getQuantity() % count.totalCount();
    }

    private boolean hasStockForFreeQuantity(OrderCommand command) {
        PromotionCount count = command.getPromotion().getCount();
        return command.getStock().getPromotionStock() >= command.getQuantity() + count.getCount();
    }

    private Boolean isApprovedForFreeQuantity(OrderCommand command, OrderApprover approver) {
        return approver.freeQuantity().approve(command.getInfo().getName());
    }

    private OrderResult createOrderResult(OrderCommand command, int totalQuantity, int freeQuantity) {
        return command.toResult(
                OrderResultQuantity.of(totalQuantity, freeQuantity),
                command.getStock().update(notChangeStock(), totalQuantity)
        );
    }
}
