package store.stock.strategy;

import store.order.dto.OrderFunction;
import store.order.dto.OrderCommand;
import store.domain.vo.ProductStock;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;

public class GeneralStockStrategy implements StockStrategy {

    @Override
    public boolean condition(OrderCommand command) {
        return hasNotValidPromotion(command) || isPromotionStockEmpty(command);
    }

    @Override
    public OrderResult process(OrderCommand command, OrderFunction function) {
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
            return notChangeStock();
        }

        return generalStock;
    }

    private OrderResult createOrderResult(OrderCommand command, int generalStock, int promotionStock) {
        return command.toResult(
                OrderResultQuantity.of(command.getQuantity(), notChangeStock()),
                command.getStock().update(generalStock, promotionStock)
        );
    }
}
