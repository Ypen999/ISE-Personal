package com.dormbill.algorithm;

import java.util.*;

public class MinTransferCalculator {
    public static class Transfer {
        private int from;
        private int to;
        private long amount;

        public Transfer(int from, int to, long amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        public int getFrom() {
            return from;
        }
        public int getTo() {
            return to;
        }
        public long getAmount() {
            return amount;
        }

    }
    private static int bestCount = Integer.MAX_VALUE;
    private static List<Transfer> bestTransfers = new ArrayList<>();

    public static List<Transfer> getBestTransfers(long[] balances) {
        calculateMinTransfers(balances);
        return bestTransfers;
    }
    public static int minTransfers(long[] balances) {
        calculateMinTransfers(balances);
        return bestCount;
    }
    public static void calculateMinTransfers(long[] balances) {
        bestCount = Integer.MAX_VALUE;
        bestTransfers.clear();
        List<Transfer> currentTransfers = new ArrayList<>();
        backtracing(balances, 0, currentTransfers);   
    }
    private static void backtracing(long[] balance,int start,List<Transfer> currentTransfers){
        while (start < balance.length && balance[start] == 0) {
            start++;
        }
        
        if(start==balance.length){
            if(currentTransfers.size()<bestCount){
                bestCount=currentTransfers.size();
                bestTransfers=new ArrayList<>(currentTransfers);
            }
            return;
        }
        for(int i=start+1;i<balance.length;i++){
            if(balance[start]>0 && balance[i]>0 || balance[start]<0 && balance[i]<0||balance[i]==0){
                continue;
            }
            long amount=Math.min(Math.abs((long)balance[start]),Math.abs((long)balance[i]));
            long startBalance=balance[start];
            long iBalance=balance[i];

            if(startBalance>0){
                balance[start]-=amount;
                balance[i]+=amount;
                currentTransfers.add(new Transfer(i,start,amount));
            }
            else{
                balance[start]+=amount;
                balance[i]-=amount;
                currentTransfers.add(new Transfer(start,i,amount));
            }
            backtracing(balance,start,currentTransfers);
            currentTransfers.remove(currentTransfers.size()-1);
            balance[start]=startBalance;
            balance[i]=iBalance;

        }
    }

}