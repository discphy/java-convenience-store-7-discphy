package store.approver;

@FunctionalInterface
public interface AgreementApprover {

    Boolean isAgree(Object... values);
}
