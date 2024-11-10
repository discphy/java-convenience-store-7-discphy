package store.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.NOT_BLANK_PROMOTION_NAME;

class PromotionTest {

    @Test
    void 프로모션명이_없으면_예외_발생() {
        //given
        PromotionCount promotionCount = new PromotionCount(2, 1);
        PromotionDate promotionDate = new PromotionDate(LocalDate.MIN, LocalDate.MAX);

        assertThatThrownBy(() -> new Promotion("", promotionCount, promotionDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_BLANK_PROMOTION_NAME.message());
    }

}