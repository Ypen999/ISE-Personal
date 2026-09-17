package com.dormbill.algorithm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 最少转账测试：官方用例 S01-S08，只断言最少笔数。
 */
public class MinTransferCalculatorTest {

    @Test
    public void testS01() {
        assertEquals(3, MinTransferCalculator.minTransfers(new long[]{21666, 1667, -8333, -15000}));
    }

    @Test
    public void testS02() {
        assertEquals(1, MinTransferCalculator.minTransfers(new long[]{-2000, 2000}));
    }

    @Test
    public void testS03() {
        assertEquals(0, MinTransferCalculator.minTransfers(new long[]{0, 0, 0}));
    }

    @Test
    public void testS04() {
        assertEquals(3, MinTransferCalculator.minTransfers(new long[]{3000, -1000, -1000, -1000}));
    }

    @Test
    public void testS05() {
        assertEquals(1, MinTransferCalculator.minTransfers(new long[]{10000, 0, -10000}));
    }

    @Test
    public void testS06() {
        assertEquals(2, MinTransferCalculator.minTransfers(new long[]{1000, 1000, -1000, -1000}));
    }

    @Test
    public void testS07() {
        assertEquals(1, MinTransferCalculator.minTransfers(new long[]{5000, -5000, 0}));
    }

    @Test
    public void testS08() {
        assertEquals(1, MinTransferCalculator.minTransfers(new long[]{1, -1}));
    }
}
