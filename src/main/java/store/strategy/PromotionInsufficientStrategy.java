package store.strategy;

import store.dto.OrderApprover;
import store.dto.OrderCommand;
import store.entity.ProductStock;
import store.entity.PromotionCount;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;

import static store.constant.OrderConstant.NOT_CHAGE_STOCK;

public class PromotionInsufficientStrategy implements StockStrategy {
    
    @Override
    public boolean condition(OrderCommand command) {
        return command.getPromotion() != null
                && command.getPromotion().isValid()
                && command.getStock().getPromotionStock() < command.getQuantity();
    }

    @Override
    public OrderResult process(OrderCommand command, OrderApprover approver) {
        ProductStock stock = command.getStock();
        PromotionCount count = command.getPromotion().getCount();

        int freeQuantity = stock.getPromotionStock() / count.totalCount();
        int totalQuantity = freeQuantity * count.totalCount();
        int fullPaymentQuantity = command.getQuantity() - totalQuantity;
        int promotionStock = totalQuantity;
        int generalStock = NOT_CHAGE_STOCK;

        if (approver.fullPayment().isAgree(command.getInfo().getName(), fullPaymentQuantity)) {
            totalQuantity += fullPaymentQuantity;
            generalStock = command.getQuantity() - stock.getPromotionStock();
            promotionStock = stock.getPromotionStock();
        }

        return command.toResult(getOrderResultQuantity(totalQuantity, freeQuantity),
                getUpdateStock(stock, generalStock, promotionStock));
    }

    private OrderResultQuantity getOrderResultQuantity(int totalQuantity, int freeQuantity) {
        return OrderResultQuantity.of(totalQuantity, freeQuantity);
    }

    private ProductStock getUpdateStock(ProductStock stock, int generalStock, int promotionStock) {
        return stock.update(generalStock, promotionStock);
    }
}
