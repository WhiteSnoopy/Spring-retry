package com.chan.retry.constant;

/**
 * @author chanyun
 */
public enum ResponseCode {
    OK("200"), // 成功响应
    PARTIAL_OK("201"), // 部分成功响应
    ILLEGAL("400"), //非法请求
    FAIL("500"); // 服务器内部错误

    private String code;

    private ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}