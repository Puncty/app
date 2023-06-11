package de.puncty.app.utility;

public interface Predicate<T, R> {
    public R fn(T item);
}
