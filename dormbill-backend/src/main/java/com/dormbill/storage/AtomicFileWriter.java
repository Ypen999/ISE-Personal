package com.dormbill.storage;

/**
 * 原子写入工具：先写临时文件，再 Files.move(ATOMIC_MOVE) 替换，
 * 写入失败不会留下半截文件。
 */
public class AtomicFileWriter {
}
