package com.ueueo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Lee
 * @date 2022-05-27 11:41
 */
public class ListUtils {

    public static <T> void moveItem(List<T> source, Predicate<T> selector, int targetIndex) {
        if (targetIndex < 0 || targetIndex > source.size() - 1) {
            throw new IndexOutOfBoundsException("targetIndex should be between 0 and " + (source.size() - 1));
        }
        T item = source.stream().filter(selector).findFirst().orElse(null);
        if (item != null) {
            int currentIndex = source.indexOf(item);
            if (currentIndex == targetIndex) {
                return;
            }
            source.remove(currentIndex);
            source.add(targetIndex, item);
        }
    }

    /**
     * Sort a list by a topological sorting, which consider their dependencies.
     *
     * @param source          A list of objects to sort
     * @param getDependencies Function to resolve the dependencies
     * @param <T>             The type of the members of values.
     *
     * @return Returns a new list ordered by dependencies.
     * If A depends on B, then B will come before than A in the resulting list.
     */
    public static <T> List<T> sortByDependencies(Iterable<T> source, Function<T, Iterable<T>> getDependencies) {
        List<T> sorted = new ArrayList<>();
        Map<T, Boolean> visited = new HashMap<>();
        for (T item : source) {
            sortByDependenciesVisit(item, getDependencies, sorted, visited);
        }
        return sorted;
    }

    /**
     * @param item            Item to resolve
     * @param getDependencies Function to resolve the dependencies
     * @param sorted          List with the sortet items
     * @param visited         Dictionary with the visited items
     * @param <T>             The type of the members of values.
     */
    private static <T> void sortByDependenciesVisit(T item,
                                                    Function<T, Iterable<T>> getDependencies,
                                                    List<T> sorted,
                                                    Map<T, Boolean> visited) {

        boolean alreadyVisited = visited.containsKey(item);

        if (alreadyVisited) {
            boolean inProcess = visited.get(item);
            if (inProcess) {
                throw new IllegalArgumentException("Cyclic dependency found! Item: " + item);
            }
        } else {
            visited.put(item, true);

            Iterable<T> dependencies = getDependencies.apply(item);
            if (dependencies != null) {
                for (T dependency : dependencies) {
                    sortByDependenciesVisit(dependency, getDependencies, sorted, visited);
                }
            }
            visited.put(item, false);
            sorted.add(item);
        }
    }
}
