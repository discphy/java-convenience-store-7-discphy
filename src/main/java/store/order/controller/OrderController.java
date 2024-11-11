package store.order.controller;

import store.common.parser.OrderItemsParser;
import store.manager.ApprovalFunction;
import store.manager.ApprovalExecutor;
import store.order.dto.OrderFunction;
import store.order.dto.OrderItems;
import store.order.service.OrderService;
import store.order.vo.OrderResults;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public OrderController(InputView inputView, OutputView outputView, OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public void run() {
        ApprovalExecutor executor = this::run;
        executor.execute(retryOrder());
    }

    private void run(ApprovalFunction function) {
        printProducts();

        OrderResults results = orderResults();

        membership(results);

        printReceipt(results);

        if (function.approve()) {
            run(function);
        }
    }

    private void printProducts() {
        outputView.printProducts(orderService.getProducts());
    }

    private OrderResults orderResults() {
        OrderItems orderItems = createOrderItems();
        OrderFunction orderFunction = createFunction();

        return orderService.order(orderItems, orderFunction);
    }

    private void membership(OrderResults results) {
        if (inputView.readAgreeMembershipDiscount()) {
            results.getOrderMembership().apply();
        }
    }

    private void printReceipt(OrderResults results) {
        outputView.printReceipt(results);
    }

    private OrderItems createOrderItems() {
        return inputView.readOrderItems(this::createOrderItems);
    }

    private OrderItems createOrderItems(String input) {
        OrderItems orderItems = OrderItemsParser.parse(input);

        orderService.validate(orderItems);

        return orderItems;
    }

    private OrderFunction createFunction() {
        return OrderFunction.builder()
                .freeQuantity(inputView::readAgreeFreeQuantity)
                .fullPayment(inputView::readAgreeFullPayment)
                .build();
    }

    private ApprovalFunction retryOrder() {
        return (values) -> inputView.readAgreeRetryOrder();
    }
}
