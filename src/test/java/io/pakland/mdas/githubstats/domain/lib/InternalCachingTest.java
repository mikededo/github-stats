package io.pakland.mdas.githubstats.domain.lib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InternalCachingTest {

    @Test
    public void shouldAddAnElement() {
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(1, 1);
        assertTrue(cache.has(1));
        assertEquals(cache.size(), 1);
    }

    @Test
    public void shouldNotAddRepeatedElement() {
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(1, 1);
        cache.add(2, 1);
        assertTrue(cache.has(1));
        assertTrue(cache.has(2));
        assertEquals(cache.size(), 2);

        cache.add(2, 1);
        assertEquals(cache.size(), 2);
    }

    @Test
    public void shouldReturnAnElementIfExists() {
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(3, 1);
        assertEquals(cache.get(3), 1);
    }

    @Test
    public void shouldReturnNullIfKeyDoesNotExist(){
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(3, 1);
        assertNull(cache.get(4));
    }

    @Test
    public void shouldDeleteExistingElement() {
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(4, 1);
        assertEquals(cache.size(), 1);

        assertEquals(cache.delete(4), 1);
        assertEquals(cache.size(), 0);
    }

    @Test
    public void shouldReturnNullIfDeletingNonExistingElement() {
        InternalCaching<Integer, Integer> cache = new InternalCaching<>();
        cache.add(4, 1);
        assertEquals(cache.size(), 1);
        assertNull(cache.delete(5));
    }
}
