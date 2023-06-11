package de.puncty.app.utility;

import java.util.ArrayList;
import java.util.List;



public class Iterator {
    public static <T, R> List<R> map(List<T> list, Predicate<T, R> pred) {
        ArrayList<R> newList = new ArrayList<>();
        for (T item : list) {
            newList.add(pred.fn(item));
        }
        return newList;
    }

    public static <T> List<T> filter(List<T> list, Predicate<T, Boolean> pred) {
        ArrayList<T> newList = new ArrayList<>();
        for (T item : list) {
            if (pred.fn(item)) {
                newList.add(item);
            }
        }
        return newList;
    }
}
