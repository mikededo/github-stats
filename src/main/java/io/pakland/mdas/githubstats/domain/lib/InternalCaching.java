package io.pakland.mdas.githubstats.domain.lib;

import java.util.*;

public class InternalCaching<K, T> {

    private final Map<K, Integer> cacheIndex = new HashMap<>();
    private final List<T> cacheStore = new ArrayList<>();

    public InternalCaching() {
    }

    protected boolean has(K key) {
        return this.cacheIndex.containsKey(key);
    }

    protected Integer size() {
        return this.cacheIndex.size();
    }

    public void add(K key, T value) {
        if (!cacheIndex.containsKey(key)) {
            cacheStore.add(value);
            cacheIndex.put(key, cacheStore.size() - 1);
        }
    }

    public T get(K key) {
        Integer index = cacheIndex.get(key);
        if (index == null) {
            return null;
        }
        return cacheStore.get(index);
    }

    public T delete(K key) {
        Integer index = cacheIndex.get(key);
        if (index == null) {
            return null;
        }
        T element = cacheStore.get(index);
        cacheStore.remove(element);
        cacheIndex.remove(key);
        return element;
    }
}
