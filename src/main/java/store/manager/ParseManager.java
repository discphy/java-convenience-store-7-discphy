package store.manager;

@FunctionalInterface
public interface ParseManager<T> {

    T parse(String input);
}
