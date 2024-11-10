package store.parser;


import store.dto.OrderItem;
import store.dto.OrderItems;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static store.constant.ErrorMessage.INVALID_INPUT_ORDER_ITEMS;

public class OrderItemsInputParser implements InputParser<OrderItems> {

    private static final String ORDER_ITEM_PATTERN = "\\[([a-zA-Z가-힣]+)-(\\d+)]";

    public OrderItems parse(String input) {
        validate(input);

        Matcher matcher = getMatcher(input);

        List<OrderItem> orderItems = new ArrayList<>();

        while (matcher.find()) {
            String productName = matcher.group(1);
            int orderQuantity = Integer.parseInt(matcher.group(2));


            orderItems.add(OrderItem.of(productName, orderQuantity));
        }

        return OrderItems.from(orderItems);
    }

    private void validate(String input) {
        if (isNotMatchOrderItem(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_ORDER_ITEMS.message());
        }
    }

    private boolean isNotMatchOrderItem(String input) {
        return !Pattern.compile("^" + ORDER_ITEM_PATTERN + "(," + ORDER_ITEM_PATTERN + ")*$").matcher(input).matches();
    }

    private Matcher getMatcher(String input) {
        return Pattern.compile(ORDER_ITEM_PATTERN).matcher(input);
    }
}
