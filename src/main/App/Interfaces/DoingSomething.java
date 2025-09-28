package Interfaces;

@FunctionalInterface
public interface DoingSomething<T> {
    void apply(T data);
}