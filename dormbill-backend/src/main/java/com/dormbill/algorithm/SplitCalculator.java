package com.dormbill.algorithm;
import java.util.Arrays;
/**
 * 分摊算法（纯函数）：无文件 IO、无 HTTP、不修改传入参数。
 * 负责 EVEN / BY_DAYS / BY_WEIGHT 三种分摊方式，并用最大余数法处理除不尽的分。
 */
public class SplitCalculator {
    static class Reminder{
        int index;
        int remainder;
        public Reminder(int index, int remainder) {
            this.index = index;
            this.remainder=remainder;
        }
    }
    public static long[] splitEven(long totalCents,int count){
        long[] result = new long[count];
        long base = totalCents / count;
        long remainder = totalCents % count;
        for (int i = 0; i < count; i++) {
            result[i] = base + (i < remainder ? 1 : 0);
        }
        return result;
    }
    public static long[] splitByDays(long totalCents, int[] days) {

        long totalDays = 0;
        for (int day : days) {
            totalDays += day;
        }
        long[] result = new long[days.length];
        Reminder[] reminders = new Reminder[days.length];
        long remainder = totalCents;

        for (int i = 0; i < days.length; i++) {
            result[i] = totalCents * days[i] / totalDays;
            reminders[i] = new Reminder(i, (int)(totalCents * days[i] % totalDays));
            remainder -= result[i];
        }
        Arrays.sort(reminders, (a, b) -> {
        if (a.remainder != b.remainder) {
            return Integer.compare(b.remainder, a.remainder);
        }
        return Integer.compare(a.index, b.index);
    });
        for (int i = 0; i < remainder; i++) {
            result[reminders[i].index]++;
        }
        return result;
    }
    public static long[] splitByWeight(long totalCents, int[] weights) {
        long totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }
        long[] result = new long[weights.length];
        Reminder[] reminders = new Reminder[weights.length];
        long remainder = totalCents;

        for (int i = 0; i < weights.length; i++) {
            result[i] = totalCents * weights[i] / totalWeight;
            reminders[i] = new Reminder(i, (int)(totalCents * weights[i] % totalWeight));
            remainder -= result[i];
        }
        Arrays.sort(reminders, (a, b) -> {
        if (a.remainder != b.remainder) {
            return Integer.compare(b.remainder, a.remainder);
        }
        return Integer.compare(a.index, b.index);
    });
        for (int i = 0; i < remainder; i++) {
            result[reminders[i].index]++;
        }
        return result;
    }
}
