package store.domain.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_PRODUCT_PRICE;
import static store.common.constant.ErrorMessage.NOT_BLANK_PRODUCT_NAME;

class ProductInfoTest {

    @Test
    void 상품_이름_미존재_시_예외_발생() {
        assertThatThrownBy(() -> new ProductInfo("", 1_000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_BLANK_PRODUCT_NAME.message());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1_000L, 0})
    void 상품_가격_0보다_같거나_작을_시_예외_발생(long price) {
        assertThatThrownBy(() -> new ProductInfo("콜라", price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PRODUCT_PRICE.message());
    }

}