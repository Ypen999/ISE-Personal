package com.dormbill.algorithm;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
/**
 * 分摊算法测试：官方用例 T01-T10。
 */
public class SplitCalculatorTest {
    @Test
    public void testT01() {
        assertArrayEquals(new long[]{20000,20000,20000}, SplitCalculator.splitEven(60000, 3));
    }
    @Test
    public void testT02() {
        assertArrayEquals(new long[]{3334,3333,3333}, SplitCalculator.splitEven(10000, 3));
    }
    @Test
    public void testT03() {
        assertArrayEquals(new long[]{2500,2500,2500,2500}, SplitCalculator.splitEven(10000, 4));
    }
    @Test
    public void testT04() {
        assertArrayEquals(new long[]{2501,2500,2500,2500}, SplitCalculator.splitEven(10001, 4));
    }
    @Test
    public void testT05() {
        assertArrayEquals(new long[]{3100,3100},SplitCalculator.splitByDays(6200, new int[]{31,31}));
    }
    @Test
    public void testT06() {
        assertArrayEquals(new long[]{2316,1569,2315},SplitCalculator.splitByDays(6200, new int[]{31,21,31}));
    }
    @Test
    public void testT07() {
        assertArrayEquals(new long[]{1000,3000,3000},SplitCalculator.splitByDays(7000, new int[]{1,3,3}));
    }
    @Test
    public void testT08() {
        assertArrayEquals(new long[]{0,2500,2500},SplitCalculator.splitByDays(5000, new int[]{0,10,10}));
    }
    @Test
    public void testT09() {
        assertArrayEquals(new long[]{3333,1667},SplitCalculator.splitByWeight(5000, new int[]{2,1}));
    }
    @Test
    public void testT10() {
        assertArrayEquals(new long[]{3000,1000},SplitCalculator.splitByWeight(4000, new int[]{3,1}));
    }

}
