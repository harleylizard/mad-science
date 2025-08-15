package com.harleylizard.madscience.common;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public final class PriorityMap<K, V extends Comparable<V>> {
    private final Map<K, Queue<V>> map;

    private PriorityMap(Map<K, Queue<V>> map) {
        this.map = map;
    }

    public void add(K k, V v) {
        var queue = map.computeIfAbsent(k, PriorityMap::queue);
        if (!queue.contains(v)) {
            queue.offer(v);
        }
    }

    public @Nullable V get(K k) {
        var queue = map.get(k);
        return queue == null ? null : queue.isEmpty() ? null : queue.peek();
    }

    public boolean has(K k) {
        return map.containsKey(k) && !map.get(k).isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public static <K, V extends Comparable<V>> PriorityMap<K, V> of() {
        return new PriorityMap<>(new HashMap<>());
    }

    private static <K, V extends Comparable<V>> Queue<V> queue(K k) {
        return new PriorityQueue<>();
    }
}
