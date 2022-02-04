package edu.emory.cs.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    @Test
    public void getMiddleIndexTest() {
        Assertions.assertEquals(5, Utils.getMiddleIndex(0, 10));
    }
}