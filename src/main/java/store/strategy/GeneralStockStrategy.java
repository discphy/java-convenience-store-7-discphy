package store.strategy;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.ProductStock;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import static store.constant.OrderConstant.NOT_CHAGE_STOCK;

public class GeneralStockStrategy implements StockStrategy {

    @Override
    public boolean condition(OrderCommand command) {
        return command.getPromotion() == null
                || !command.getPromotion().isValid()
                || command.getStock().getPromotionStock() == 0;
    }

    @Override
    public OrderResult process(OrderCommand command, OrderApprover approver) {
        return command.toResult(getOrderResultQuantity(command), getUpdateStock(command));
    }

    private OrderResultQuantity getOrderResultQuantity(OrderCommand command) {
        return OrderResultQuantity.of(command.getQuantity(), NOT_CHAGE_STOCK);
    }

    private ProductStock getUpdateStock(OrderCommand command) {
        ProductStock stock = command.getStock();

        int generalStock = command.getQuantity() - stock.getPromotionStock();
        int promotionStock = stock.getPromotionStock();

        if (stock.getPromotionStock() > command.getQuantity()) {
            promotionStock = command.getQuantity();
            generalStock = 0;
        }

        return stock.update(generalStock, promotionStock);
    }
}
