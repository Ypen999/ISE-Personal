package com.dormbill.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dormbill.model.Bill;
import com.dormbill.model.Member;
import com.dormbill.model.Period;
import com.dormbill.model.SplitMethod;

/**
 * 净余额计算（纯函数）：净余额 = 累计垫付 - 累计分摊。
 */
public class BalanceCalculator {


    public static Map<String, Long> calculateBalance(List<Bill> bills, List<Member> members) {
        // 成员列表转成 Map，方便按 id 查入住/退宿日期（BY_DAYS 用）
        Map<String, Member> memberById = new HashMap<>();
        for (Member m : members) {
            memberById.put(m.getId(), m);
        }

        Map<String, Long> balanceMap = new HashMap<>();

        for (Bill bill : bills) {
            String payerId = bill.getPayerId();
            long amountCents = bill.getAmountCents();
            List<String> participantIds = bill.getParticipants();
            SplitMethod method = bill.getMethod();

            // 三种方式最终都得到 long[] shares：每人分摊额，下标与 participantIds 一一对应
            long[] shares;
            switch (method) {
                case EVEN:
                    shares = SplitCalculator.splitEven(amountCents, participantIds.size());
                    break;
                case BY_WEIGHT: {
                    List<Integer> weightList = bill.getWeights();
                    int[] weights = new int[weightList.size()];
                    for (int i = 0; i < weights.length; i++) {
                        weights[i] = weightList.get(i);
                    }
                    shares = SplitCalculator.splitByWeight(amountCents, weights);
                    break;
                }
                case BY_DAYS: {
                    Period period = bill.getPeriod();
                    int[] days = new int[participantIds.size()];
                    for (int i = 0; i < participantIds.size(); i++) {
                        Member m = memberById.get(participantIds.get(i));
                        days[i] = SplitCalculator.effectiveDays(period, m.getJoinDate(), m.getLeaveDate());
                    }
                    shares = SplitCalculator.splitByDays(amountCents, days);
                    break;
                }
                default:
                    throw new IllegalStateException("未知分摊方式: " + method);
            }

            // 垫付人先垫了整笔钱，净余额加 amountCents
            balanceMap.put(payerId, balanceMap.getOrDefault(payerId, 0L) + amountCents);

            // 每个参与人要分摊自己那份，净余额减 shares[i]
            for (int i = 0; i < participantIds.size(); i++) {
                String pid = participantIds.get(i);
                balanceMap.put(pid, balanceMap.getOrDefault(pid, 0L) - shares[i]);
            }
        }

        return balanceMap;
    }
}
