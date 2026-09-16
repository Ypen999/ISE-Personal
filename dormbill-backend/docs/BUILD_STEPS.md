# 后端开发步骤

> 技术栈：Java 25 + Spring Boot 4.0.7 + Maven + JUnit 5（编译目标 Java 21）
> 核心原则：**自底向上、测试先行、先啃分值最高的算法层**

## 为什么是这个顺序

算法层（分摊 + 余额 + 最少转账）占必做 60 分里的 **42 分**，又是源码理解 40 分里至少 30 分的问答对象，而且完全不依赖 Spring，可以先写、先测、先绿。不要一上来写 Controller。

---

## 第 0 步：环境与基线

- 骨架已能 `mvn compile`（Spring Boot 4.0.7 依赖已解析成功）。
- `git init`，提交第一个 commit 作为基线。

**完成标准**：`mvn compile` 通过，git 有初始提交。

---

## 第 1 步：数据模型 `model/`（定契约）

给 `Book / Member / Bill` 填字段，严格对齐任务书 6.1 的 JSON 结构（字段名、`amountCents`、`period`、`participants` 数组等）。这是所有层的"共同语言"。

- 金额一律 `long` + `Cents` 后缀。
- 用 `record`（不可变，契合"算法不改入参"）或 POJO，二选一定死。

**完成标准**：成员/账单字段齐全，金额用 `long` + `Cents` 后缀。

---

## 第 2 步：算法层 + 官方测试（最核心，测试先行）

官方用例就是现成的测试规格，用 TDD：

1. 先写 `SplitCalculatorTest`（T01–T10，精确断言）→ 实现 `SplitCalculator` 至全绿。
2. 写 `BalanceCalculatorTest`（T11–T12）→ 实现 `BalanceCalculator` 至全绿。
3. 写 `MinTransferCalculatorTest`（S01–S08）→ 实现 `MinTransferCalculator` 至全绿。

- 算法类里不得出现 `File`、`HttpServletRequest`、任何 `@` 注解。
- 最大余数法用**整数运算**，不碰浮点；最少转账用零和子集 + 位掩码 DP。

**完成标准**：`mvn test` 算法 3 个测试类全绿，且能逐行讲清楚。

---

## 第 3 步：存储层 `storage/`

实现 `StorageService`（读写 JSON/JSONL、按账本目录隔离）+ `AtomicFileWriter`（临时文件 + `Files.move(ATOMIC_MOVE)`）。

写 `StorageServiceTest`：并发写不损坏、写中断后原文件仍可读、原子替换正确。

**完成标准**：并发写入测试（模拟 5 并发）跑绿。

---

## 第 4 步：校验 + 统一异常

`Validator`（字段规则收口）+ `ErrorCode` / `ApiException` / `GlobalExceptionHandler`（统一 `{"error":{code,message,field}}` 结构）。

**完成标准**：错误格式与状态码约定定好，可供所有 Controller 复用。

---

## 第 5 步：Service 业务编排层

按依赖顺序：`BookService` → `MemberService` → `BillService`（用算法层做预览/保存）→ `SettlementService`（余额 + 最少转账）→ `ExportService`。

每个 Service 只做「校验 → 调算法 → 调存储」的串接。

**完成标准**：各业务逻辑闭环，算法层和存储层互不直接依赖。

---

## 第 6 步：Controller + API 集成测试

五个 Controller 照任务书 6.3 路径，配 MockMvc 集成测试（多账本隔离、删除保护 409、404/400/422、CSV 守恒）。

**完成标准**：`mvn test` 一条命令跑完全部测试且全绿。

---

## 第 7 步：F10 历史趋势（选做）

`GET /api/books/{bookId}/statistics/monthly`，返回近 6 个月支出/人均，无数据月份补 0。

**完成标准**：返回结构化 JSON，无数据月份为 0。

---

## 第 8 步：交付物收尾

补 `README.md`、`docs/API.md`、`docs/SELF_CHECK.md`，按任务书打包 `学号-姓名-dormbill-backend.zip`，自查清单逐项打勾。

**完成标准**：解压后能按 README 完成安装、测试、启动。

---

## 关键提醒

- **每一步对应一个 git commit**（头歌平台要提交历史），别攒到最后一次提交。
- **第 2 步单独多花功夫**——分值最高，也最该被讲透。
