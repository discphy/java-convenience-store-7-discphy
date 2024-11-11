package store.common.formatter;

import org.junit.jupiter.api.Test;
import store.domain.vo.ProductInfo;
import store.order.vo.OrderResult;
import store.order.vo.OrderResultQuantity;
import store.order.vo.OrderResults;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResultsFormatterTest {

    @Test
    void 영수증_출력_테스트() {
        //given
        OrderResults orderResults = supportOrderResults();
        orderResults.getOrderMembership().apply();

        //when
        String message = OrderResultsFormatter.format(orderResults);

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