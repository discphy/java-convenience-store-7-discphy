package store.manager;

@FunctionalInterface
public interface ApprovalFunction {

    Boolean approve(Object... values);
}
