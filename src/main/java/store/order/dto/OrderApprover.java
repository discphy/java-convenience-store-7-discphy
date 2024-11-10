package store.order.dto;

import store.manager.AgreementApprover;

public class OrderApprover {

    private final AgreementApprover freeQuantityApprover;
    private final AgreementApprover fullPaymentApprover;

    public OrderApprover(OrderApproverBuilder builder) {
        freeQuantityApprover = builder.freeQuantityApprover;
        fullPaymentApprover = builder.fullPaymentApprover;
    }

    public static OrderApproverBuilder builder() {
        return new OrderApproverBuilder();
    }

    public AgreementApprover freeQuantity() {
        return freeQuantityApprover;
    }

    public AgreementApprover fullPayment() {
        return fullPaymentApprover;
    }

    public static class OrderApproverBuilder {

        private AgreementApprover freeQuantityApprover;
        private AgreementApprover fullPaymentApprover;

        public OrderApproverBuilder freeQuantity(AgreementApprover freeQuantityApprover) {
            this.freeQuantityApprover = freeQuantityApprover;
            return this;
        }

        public OrderApproverBuilder fullPayment(AgreementApprover fullPaymentApprover) {
            this.fullPaymentApprover = fullPaymentApprover;
            return this;
        }

        public OrderApprover build() {
            return new OrderApprover(this);
        }
    }
}
