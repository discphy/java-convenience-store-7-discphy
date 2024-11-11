package store;

import store.config.ApplicationConfig;
import store.config.DataInitializer;
import store.order.controller.OrderController;

public class Application {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();

        DataInitializer dataInitializer = config.dataInitializer();
        OrderController orderController = config.orderController();

        dataInitializer.init();
        orderController.run();
    }
}
