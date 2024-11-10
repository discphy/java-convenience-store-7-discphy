package store.common.parser;


import store.order.dto.OrderItem;
import store.order.dto.OrderItems;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static store.common.constant.ErrorMessage.INVALID_INPUT_ORDER_ITEMS;

public class OrderItemsParser {

    private static final String ORDER_ITEM_PATTERN = "\\[([a-zA-Z가-힣]+)-(\\d+)]";

    public static OrderItems parse(String input) {
        validate(input);

        Matcher matcher = getMatcher(input);
        List<OrderItem> orderItems = new ArrayList<>();

        while (matcher.find()) {
            orderItems.add(OrderItem.of(matcher.group(1), Integer.parseInt(matcher.group(2))));
        }

        return OrderItems.from(orderItems);
    }

    private static void validate(String input) {
        if (isNotMatchOrderItem(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_ORDER_ITEMS.message());
        }
    }

    private static boolean isNotMatchOrderItem(String input) {
        return !Pattern.compile("^" + ORDER_ITEM_PATTERN + "(," + ORDER_ITEM_PATTERN + ")*$").matcher(input).matches();
    }

    private static Matcher getMatcher(String input) {
        return Pattern.compile(ORDER_ITEM_PATTERN).matcher(input);
    }
}
