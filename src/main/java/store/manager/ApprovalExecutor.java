package store.manager;

@FunctionalInterface
public interface ApprovalExecutor {

    void execute(AgreementApprover approver);
}
