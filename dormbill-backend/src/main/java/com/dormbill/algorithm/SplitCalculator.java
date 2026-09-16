package com.dormbill.algorithm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import com.dormbill.model.Period;

/**
 * 分摊算法（纯函数）：无文件 IO、无 HTTP、不修改传入参数。
 * 负责 EVEN / BY_DAYS / BY_WEIGHT 三种分摊方式，并用最大余数法处理除不尽的分。
 */
public class SplitCalculator {
    static class Reminder {
        int index;
        int remainder;

        public Reminder(int index, int remainder) {
            this.index = index;
            this.remainder = remainder;
        }
    }

    public static long[] splitEven(long totalCents, int count) {
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
            reminders[i] = new Reminder(i, (int) (totalCents * days[i] % totalDays));
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
            reminders[i] = new Reminder(i, (int) (totalCents * weights[i] % totalWeight));
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

    /**
     * 计算某个成员在账单周期内的有效在住天数（任务书 2.3）。
     * 入住日和退宿日均计入；leaveDate 为 null 表示仍在住。
     */
    public static int effectiveDays(Period period, String joinDate, String leaveDate) {
        // 日期字符串是 "2026-03-01" 标准 ISO 格式，LocalDate.parse 直接识别
        LocalDate periodStart = LocalDate.parse(period.start());
        LocalDate periodEnd = LocalDate.parse(period.end());
        LocalDate join = LocalDate.parse(joinDate);
        LocalDate leave = (leaveDate == null) ? null : LocalDate.parse(leaveDate);

        // 交集起点 = 账单开始日 与 入住日 的较晚者
        LocalDate start = join.isAfter(periodStart) ? join : periodStart;
        // 交集终点 = 账单结束日 与 退宿日 的较早者；没退宿(null)则用账单结束日
        LocalDate end = (leave == null || leave.isAfter(periodEnd)) ? periodEnd : leave;

        // 起点晚于终点 = 没有交集，有效天数 0
        if (start.isAfter(end)) {
            return 0;
        }
        // 两端都算一天，所以天数 = 相差天数 + 1
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
}
