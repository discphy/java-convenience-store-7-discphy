package store.parser;

@FunctionalInterface
public interface InputParser<T> {

    T parse(String input);
}
