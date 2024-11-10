package store.strategy;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.PromotionCount;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import static store.constant.OrderConstant.NOT_CHANGE_STOCK;

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
        return approver.freeQuantity().isAgree(command.getInfo().getName());
    }

    private OrderResult createOrderResult(OrderCommand command, int totalQuantity, int freeQuantity) {
        return command.toResult(
                OrderResultQuantity.of(totalQuantity, freeQuantity),
                command.getStock().update(NOT_CHANGE_STOCK, totalQuantity)
        );
    }
}
