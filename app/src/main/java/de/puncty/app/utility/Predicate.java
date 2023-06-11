package de.puncty.app.utility;

/** interface for generic lambda, where T is the item type and R is the return type */
 public interface Predicate<T, R> {
    R fn(T item);
}
