package store.strategy;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.ProductStock;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import static store.constant.OrderConstant.NOT_CHANGE_STOCK;

public class GeneralStockStrategy implements StockStrategy {

    @Override
    public boolean condition(OrderCommand command) {
        return hasNotValidPromotion(command) || isPromotionStockEmpty(command);
    }

    @Override
    public OrderResult process(OrderCommand command, OrderApprover approver) {
        ProductStock stock = command.getStock();

        int promotionStock = Math.min(stock.getPromotionStock(), command.getQuantity());
        int generalStock = calculateGeneralStock(command);

        return createOrderResult(command, generalStock, promotionStock);
    }

    private boolean hasNotValidPromotion(OrderCommand command) {
        return command.getPromotion() == null || !command.getPromotion().isValid();
    }

    private boolean isPromotionStockEmpty(OrderCommand command) {
        return command.getStock().getPromotionStock() == 0;
    }

    private int calculateGeneralStock(OrderCommand command) {
        ProductStock stock = command.getStock();
        int generalStock = command.getQuantity() - stock.getPromotionStock();

        if (stock.getPromotionStock() > command.getQuantity()) {
            return NOT_CHANGE_STOCK;
        }

        return generalStock;
    }

    private OrderResult createOrderResult(OrderCommand command, int generalStock, int promotionStock) {
        return command.toResult(
                OrderResultQuantity.of(command.getQuantity(), NOT_CHANGE_STOCK),
                command.getStock().update(generalStock, promotionStock)
        );
    }
}
