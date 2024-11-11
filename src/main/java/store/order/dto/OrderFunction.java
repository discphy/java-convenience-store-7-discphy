package store.order.dto;

import store.manager.ApprovalFunction;

public class OrderFunction {

    private final ApprovalFunction freeQuantity;
    private final ApprovalFunction fullPayment;

    public OrderFunction(OrderFunctionBuilder builder) {
        freeQuantity = builder.freeQuantity;
        fullPayment = builder.fullPayment;
    }

    public static OrderFunctionBuilder builder() {
        return new OrderFunctionBuilder();
    }

    public ApprovalFunction freeQuantity() {
        return freeQuantity;
    }

    public ApprovalFunction fullPayment() {
        return fullPayment;
    }

    public static class OrderFunctionBuilder {

        private ApprovalFunction freeQuantity;
        private ApprovalFunction fullPayment;

        public OrderFunctionBuilder freeQuantity(ApprovalFunction freeQuantity) {
            this.freeQuantity = freeQuantity;
            return this;
        }

        public OrderFunctionBuilder fullPayment(ApprovalFunction fullPayment) {
            this.fullPayment = fullPayment;
            return this;
        }

        public OrderFunction build() {
            return new OrderFunction(this);
        }
    }
}
