package com.netease.mail.retry.constant;

/**
 * @author chanyun
 */
public enum ErrorCode {
    INVALID_DEVICE, // 未识别的设备
    TOKEN_MISMATCH, // token不匹配
    COMMENT_NOT_EXIST, // 晒单或者评论已经不存在
    BLOCKED, // 被屏蔽
    MISSING_PARAMETER,
    INVALID_PARAMETER,
    ILLEGAL_PARAMETER,
    ANTISPAM_REJECT; // 反垃圾检查没有通过
}
