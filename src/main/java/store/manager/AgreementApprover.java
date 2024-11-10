package store.manager;

@FunctionalInterface
public interface AgreementApprover {

    Boolean approve(Object... values);
}
