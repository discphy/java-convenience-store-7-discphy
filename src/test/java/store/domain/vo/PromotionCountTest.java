package store.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_PROMOTION_BUY_COUNT;
import static store.common.constant.ErrorMessage.INVALID_PROMOTION_GET_COUNT;

class PromotionCountTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void 프로모션_구매_개수가_0보다_크지않으면_예외발생(int buyCount) {
        //when
        assertThatThrownBy(() -> new PromotionCount(buyCount, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PROMOTION_BUY_COUNT.message());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 2, 3})
    void 프로모션_증정_개수가_1이_아니면_예외발생(int getCount) {
        //when
        assertThatThrownBy(() -> new PromotionCount(10, getCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PROMOTION_GET_COUNT.message());
    }

}