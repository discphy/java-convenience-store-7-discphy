package store.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.dto.OrderItem;
import store.dto.OrderItems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.INVALID_INPUT_ORDER_ITEMS;

class OrderItemsInputParserTest {

    OrderItemsInputParser parser;

    @BeforeEach
    void setUp() {
        parser = new OrderItemsInputParser();
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-콜라]", "콜라-4", "4-4", "[콜라-4],에너지바-4]", "콜라-4,에너지바-5", "[콜라-4,에너지바-5"})
    void 입력값이_잘못되면_예외발생(String input) {
        //when
        assertThatThrownBy(() -> parser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_ORDER_ITEMS.message());
    }

    @Test
    void 입력값을_주문_상품_객체로_반환한다() {
        //given
        String input = "[콜라-10],[오렌지주스-2]";

        //when
        OrderItems orderItems = parser.parse(input);

        //then
        assertThat(orderItems.getOrderItems()).extracting(OrderItem::getProductName).containsExactly("콜라", "오렌지주스");
        assertThat(orderItems.getOrderItems()).extracting(OrderItem::getQuantity).containsExactly(10, 2);
    }
}