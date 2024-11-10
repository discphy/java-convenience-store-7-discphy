package store.format;

import org.junit.jupiter.api.Test;
import store.entity.ProductInfo;
import store.vo.OrderResult;
import store.vo.OrderResultQuantity;
import store.vo.OrderResults;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResultsMessageFormatterTest {

    @Test
    void 영수증_출력_테스트() {
        //given
        MessageFormatter<OrderResults> formatter = new OrderResultsMessageFormatter();
        OrderResults orderResults = supportOrderResults();
        orderResults.getOrderMembership().apply();

        //when
        String message = formatter.format(orderResults);

        //then
        assertThat(message).containsIgnoringWhitespaces("내실돈9,000");
    }

    static OrderResults supportOrderResults() {
        List<OrderResult> orderResults = List.of(
                OrderResult.of(
                        new ProductInfo("콜라", 1_000L),
                        OrderResultQuantity.of(3, 1),
                        null
                ),
                OrderResult.of(
                        new ProductInfo("에너지바", 2_000L),
                        OrderResultQuantity.of(5, 0),
                        null
                )
        );

        return new OrderResults(orderResults);
    }
}