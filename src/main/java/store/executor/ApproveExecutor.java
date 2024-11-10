package store.executor;

import store.approver.AgreementApprover;

@FunctionalInterface
public interface ApproveExecutor {

    void execute(AgreementApprover approver);
}
