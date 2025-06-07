package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkipListTest {
    private SkipList skipList;

    @BeforeEach
    void setUp() {
        skipList = new SkipList();
    }

    @Test
    void testInsertAndSearch() {
        skipList.insert(5);
        skipList.insert(3);
        skipList.insert(7);

        assertTrue(skipList.search(5));
        assertTrue(skipList.search(3));
        assertTrue(skipList.search(7));
        assertFalse(skipList.search(10));
    }


    @Test
    void testDelete() {
        skipList.insert(5);
        skipList.insert(3);

        skipList.remove(5);
        assertFalse(skipList.search(5));
        assertTrue(skipList.search(3));
    }
    @Test
    void testEmptyList() {
        assertFalse(skipList.search(5));
        assertEquals(skipList.size, 0);
    }

    @Test
    void testInsertRemoveSingleElement() {
        skipList.insert(10);
        assertTrue(skipList.search(10));
        skipList.remove(10);
        assertFalse(skipList.search(10));
    }

    @Test
    void testInsertNegativeNumbers() {
        skipList.insert(-5);
        skipList.insert(-10);
        assertTrue(skipList.search(-5));
        assertTrue(skipList.search(-10));
    }
}