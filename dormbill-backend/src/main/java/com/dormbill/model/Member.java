package com.dormbill.model;

/**
 * 成员实体：id、姓名、入住日期、退宿日期（可空，null 表示仍在住）。
 */
public class Member {
    private String id;
    private String name;
    private String joinDate;
    private String leaveDate; // 可空，null 表示仍在住

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

}
