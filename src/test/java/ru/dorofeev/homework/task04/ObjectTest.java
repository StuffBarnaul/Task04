package ru.dorofeev.homework.task04;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ObjectTest {
    ClassForCleaning test = new ClassForCleaning();
    Set<String> fieldsToCleanup = new HashSet<String>();
    Set<String> fieldsToOutput = new HashSet<String>();
    CleanUpClass cleaner = new CleanUpClass();

    @Before
    public void setUp() throws Exception {
        fieldsToCleanup.add("one");
        fieldsToCleanup.add("list");
        fieldsToOutput.add("two");
        fieldsToOutput.add("set");
    }

    @Test
    public void cleanupObject() {
        cleaner.cleanup(test,fieldsToCleanup,fieldsToOutput);
        assertEquals(test.one,0);
        assertEquals(test.list,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Exception1() {
        fieldsToCleanup.add("tyu");
        cleaner.cleanup(test,fieldsToCleanup,fieldsToOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Exception2() {
        fieldsToOutput.add("tyu");
        cleaner.cleanup(test,fieldsToCleanup,fieldsToOutput);
    }
}