package store;

import store.controller.OrderController;

public class Application {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        OrderController orderController = config.orderController();

        orderController.run();
    }
}
