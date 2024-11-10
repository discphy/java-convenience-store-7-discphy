package store.controller;

import store.approver.AgreementApprover;
import store.dto.OrderApprover;
import store.dto.OrderItems;
import store.executor.ApproveExecutor;
import store.initializer.DataInitializer;
import store.parser.InputParser;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;
import store.vo.OrderResults;

public class OrderController {

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;
    private final DataInitializer dataInitializer;
    private final InputParser<OrderItems> orderItemsInputParser;

    public OrderController(InputView inputView,
                           OutputView outputView,
                           OrderService orderService, DataInitializer dataInitializer,
                           InputParser<OrderItems> orderItemsInputParser) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
        this.dataInitializer = dataInitializer;
        this.orderItemsInputParser = orderItemsInputParser;
    }

    public void run() {
        dataInitializer.init();

        ApproveExecutor executor = this::run;
        executor.execute(retryOrder());
    }

    private void run(AgreementApprover approver) {
        outputView.printProducts(orderService.getProducts());
        OrderResults results = orderService.order(createOrderItems(), createApprover());

        membership(results);
        outputView.printReceipt(results);

        if (approver.isAgree()) {
            run(approver);
        }
    }

    private OrderItems createOrderItems() {
        return inputView.readOrderItems(this::createOrderItems);
    }

    private OrderItems createOrderItems(String input) {
        OrderItems orderItems = orderItemsInputParser.parse(input);

        orderService.validate(orderItems);

        return orderItems;
    }

    private OrderApprover createApprover() {
        return OrderApprover.builder()
                .freeQuantity(inputView::readAgreeFreeQuantity)
                .fullPayment(inputView::readAgreeFullPayment)
                .build();
    }

    private void membership(OrderResults results) {
        if (inputView.readAgreeMembershipDiscount()) {
            results.getOrderMembership().apply();
        }
    }

    private AgreementApprover retryOrder() {
        return (values) -> inputView.readAgreeRetryOrder();
    }
}
