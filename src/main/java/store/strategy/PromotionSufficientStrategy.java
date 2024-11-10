package store.strategy;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.ProductStock;
import store.entity.PromotionCount;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import static store.constant.OrderConstant.NOT_CHAGE_STOCK;

public class PromotionSufficientStrategy implements StockStrategy {

    @Override
    public boolean condition(OrderCommand command) {
        return command.getPromotion() != null
                && command.getPromotion().isValid()
                && command.getStock().getPromotionStock() >= command.getQuantity();
    }

    @Override
    public OrderResult process(OrderCommand command, OrderApprover approver) {
        ProductStock stock = command.getStock();
        PromotionCount count = command.getPromotion().getCount();

        int totalQuantity = command.getQuantity();
        int freeQuantity = command.getQuantity() / count.totalCount();

        boolean isAddFreeQuantity = count.buyCount() == command.getQuantity() % count.totalCount()
                && stock.getPromotionStock() >= command.getQuantity() + count.getCount();

        if (isAddFreeQuantity && approver.freeQuantity().isAgree(command.getInfo().getName())) {
            freeQuantity++;
            totalQuantity++;
        }

        return command.toResult(
                getOrderResultQuantity(totalQuantity, freeQuantity),
                getUpdateStock(stock, totalQuantity));
    }

    private OrderResultQuantity getOrderResultQuantity(int totalQuantity, int freeQuantity) {
        return OrderResultQuantity.of(totalQuantity, freeQuantity);
    }

    private ProductStock getUpdateStock(ProductStock stock, int promotionQuantity) {
        return stock.update(NOT_CHAGE_STOCK, promotionQuantity);
    }
}
