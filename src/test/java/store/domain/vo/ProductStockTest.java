package store.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_PRODUCT_STOCK;

class ProductStockTest {

    @Test
    void 상품_재고의_일반재고_수가_0보다_작으면_예외_발생() {
        //when
        assertThatThrownBy(() -> new ProductStock(-1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PRODUCT_STOCK.message());
    }

    @Test
    void 상품_재고의_프로모션재고_수가_0보다_작으면_예외_발생() {
        //when
        assertThatThrownBy(() -> new ProductStock(19, -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PRODUCT_STOCK.message());
    }
}