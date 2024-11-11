package store.stock.strategy;

import store.order.dto.OrderFunction;
import store.order.dto.OrderCommand;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;

public class PromotionInsufficientStrategy implements StockStrategy {
    
    @Override
    public boolean condition(OrderCommand command) {
        return hasValidPromotion(command) && hasNotSufficuentPromotionStock(command);
    }

    @Override
    public OrderResult process(OrderCommand command, OrderFunction function) {
        int totalQuantity = calculateTotalQuantity(command);
        int freeQuantity = calculateFreeQuantity(command);
        int fullPaymentQuantity = command.getQuantity() - totalQuantity;

        if (isApprovedForFullPayment(command, function, fullPaymentQuantity)) {
            return createOrderResult(command, totalQuantity + fullPaymentQuantity, freeQuantity,
                    command.getQuantity() - command.getStock().getPromotionStock(),
                    command.getStock().getPromotionStock());
        }

        return createOrderResult(command, totalQuantity, freeQuantity, notChangeStock(), totalQuantity);
    }

    private boolean hasNotSufficuentPromotionStock(OrderCommand command) {
        return command.getStock().getPromotionStock() < command.getQuantity();
    }

    private boolean hasValidPromotion(OrderCommand command) {
        return command.getPromotion() != null && command.getPromotion().isValid();
    }

    private int calculateFreeQuantity(OrderCommand command) {
        return command.getStock().getPromotionStock() / command.getPromotion().getCount().totalCount();
    }

    private int calculateTotalQuantity(OrderCommand command) {
        return calculateFreeQuantity(command) * command.getPromotion().getCount().totalCount();
    }

    private Boolean isApprovedForFullPayment(OrderCommand command, OrderFunction function, int fullPaymentQuantity) {
        return function.fullPayment().approve(command.getInfo().getName(), fullPaymentQuantity);
    }

    private OrderResult createOrderResult(OrderCommand command,
                                          int totalQuantity,
                                          int freeQuantity,
                                          int generalStock,
                                          int promotionStock) {
        return command.toResult(
                OrderResultQuantity.of(totalQuantity, freeQuantity),
                command.getStock().update(generalStock, promotionStock));
    }
}
