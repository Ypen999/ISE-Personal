package com.dormbill.model;
import java.util.List;
/**
 * 账单实体：名称、金额（单位分，字段用 Cents 后缀）、记账日期、
 * 分摊方式、参与人列表、垫付人、按天周期或指定权重。
 */
public class Bill {
    // 账单唯一标识，对应 JSON "id"（例："b001"）。原来缺失，补上——查询/改/删都要靠它定位。
      private String id;
      // 所属账本 id，对应 JSON "bookId"。原字段名就是 bookId，这里补上 private + getter/setter。
      private String bookId;
      // 账单名称，对应 JSON "title"。原来叫 name，与 JSON 不一致，改为 title。
      private String title;
      // 总金额（单位：分），对应 JSON "amountCents"。金额必须用 long + Cents 后缀。
      private long amountCents;
      // 记账日期，对应 JSON "date"。
      private String date;
      // 分摊方式，对应 JSON "method"。
      private SplitMethod method;
      // 参与人 id 列表，对应 JSON "participants"。原来是 String[] members，改为 List<String> participants。
      private List<String> participants;
      // 垫付人 id（必须属于 participants），对应 JSON "payerId"
      private String payerId;
      // 按天分摊周期，对应 JSON "period"。原来是 int[] days（语义错误），改为 Period。
      private Period period;
      // 与 participants 按下标一一对应：第 i 个参与人分到第 i 个权重。
      private List<Integer> weights;
      // ---- 以下是 getter/setter：字段是 private，外部（Service/Storage/Jackson）靠这些方法读写 ----

      public String getId() { return id; }
      public void setId(String id) { this.id = id; }
      public String getBookId() { return bookId; }
      public void setBookId(String bookId) { this.bookId = bookId; }
      public String getTitle() { return title; }
      public void setTitle(String title) { this.title = title; }
      public long getAmountCents() { return amountCents; }
      public void setAmountCents(long amountCents) { this.amountCents = amountCents; }
      public String getDate() { return date; }
      public void setDate(String date) { this.date = date; }
      public SplitMethod getMethod() { return method; }
      public void setMethod(SplitMethod method) { this.method = method; }
      public List<String> getParticipants() { return participants; }
      public void setParticipants(List<String> participants) { this.participants = participants; }
      public String getPayerId() { return payerId; }
      public void setPayerId(String payerId) { this.payerId = payerId; }
      public Period getPeriod() { return period; }
      public void setPeriod(Period period) { this.period = period; }
      public List<Integer> getWeights() { return weights; }
      public void setWeights(List<Integer> weights) { this.weights = weights; }
}
