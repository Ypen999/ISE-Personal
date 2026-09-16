package com.dormbill.model;

/**
 * 按天分摊周期（不可变）：start 起始日期、end 结束日期，均含当天。
 *
 * 对应 JSON 里的 "period": { "start": "...", "end": "..." }。
 * 用 record 声明：编译器会自动生成构造器、以及 start() 和 end() 两个只读访问器。
 */
public record Period(String start, String end) {
}
