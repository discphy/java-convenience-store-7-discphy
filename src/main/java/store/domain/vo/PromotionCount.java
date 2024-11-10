package store.domain.vo;

import static store.common.constant.ErrorMessage.INVALID_PROMOTION_BUY_COUNT;
import static store.common.constant.ErrorMessage.INVALID_PROMOTION_GET_COUNT;

public class PromotionCount {

    private static final int DEFAULT_GET_COUNT = 1;
    private final int buyCount;
    private final int getCount;

    public PromotionCount(int buyCount, int getCount) {
        validateBuyCount(buyCount);
        validateGetCount(getCount);

        this.buyCount = buyCount;
        this.getCount = DEFAULT_GET_COUNT;
    }

    public int buyCount() {
        return buyCount;
    }

    public int getCount() {
        return getCount;
    }

    public int totalCount() {
        return buyCount + getCount;
    }

    private void validateBuyCount(int buyCount) {
        if (buyCount <= 0) {
            throw new IllegalArgumentException(INVALID_PROMOTION_BUY_COUNT.message());
        }
    }

    private void validateGetCount(int getCount) {
        if (getCount != DEFAULT_GET_COUNT) {
            throw new IllegalArgumentException(INVALID_PROMOTION_GET_COUNT.message());
        }
    }
}
