package store.common.formatter;

import org.junit.jupiter.api.Test;
import store.domain.entity.*;
import store.domain.vo.ProductInfo;
import store.domain.vo.ProductStock;
import store.domain.vo.PromotionCount;
import store.domain.vo.PromotionDate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductFormatterTest {

    @Test
    void 상품_출력_테스트() {
        //given
        Product product = new Product(new ProductInfo("콜라", 1_000L), new ProductStock(10, 0),
                new Promotion("탄산2+1", new PromotionCount(2, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX)));

        //when
        String format = ProductFormatter.format(product);

        //then
        assertThat(format).contains("- 콜라 1,000원 재고 없음 탄산2+1\n- 콜라 1,000원 10개");
    }

}