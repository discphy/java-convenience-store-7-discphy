package store.format;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.entity.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMessageFormatterTest {

    @Test
    void 상품_출력_테스트() {
        //given
        ProductMessageFormatter formatter = new ProductMessageFormatter();
        Product product = new Product(new ProductInfo("콜라", 1_000L), new ProductStock(10, 0),
                new Promotion("탄산2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)));

        //when
        String format = formatter.format(product);

        //then
        assertThat(format).contains("- 콜라 1,000원 재고 없음 탄산2+1\n- 콜라 1,000원 10개");
    }

}