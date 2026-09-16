package com.dormbill.algorithm;
 import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import com.dormbill.model.Bill;
import com.dormbill.model.Member;
import com.dormbill.model.SplitMethod;

/**
 * 余额与结算测试：官方用例 T11-T12。
 */
public class BalanceCalculatorTest {
    @Test
    public void testT11() {
        Member m1=new Member(); m1.setId("m001"); m1.setName("张三");
        Member m2=new Member(); m2.setId("m002"); m2.setName("李四");
        Member m3=new Member(); m3.setId("m003"); m3.setName("王五");
        Member m4=new Member(); m4.setId("m004"); m4.setName("赵六");
        List<Member> members=List.of(m1,m2,m3,m4);
        Bill b1=new Bill(); b1.setId("b001"); b1.setMethod(SplitMethod.EVEN); b1.setAmountCents(40000); b1.setPayerId("m001"); b1.setParticipants(List.of("m001","m002","m003","m004"));
        Bill b2=new Bill(); b2.setId("b002"); b2.setMethod(SplitMethod.EVEN); b2.setAmountCents(20000); b2.setPayerId("m002"); b2.setParticipants(List.of("m001","m002","m003","m004")); 
        Bill b3=new Bill(); b3.setId("b003"); b3.setMethod(SplitMethod.EVEN); b3.setAmountCents(10000); b3.setPayerId("m003"); b3.setParticipants(List.of("m001","m002","m003"));
        List<Bill> bills=List.of(b1,b2,b3);
        Map<String,Long> balance=BalanceCalculator.calculateBalance(bills,members);
        assertEquals(21666L, balance.get("m001"));  // 张三
        assertEquals(1667L,  balance.get("m002"));  // 李四
        assertEquals(-8333L, balance.get("m003"));  // 王五
        assertEquals(-15000L, balance.get("m004")); // 赵六
    }
}
