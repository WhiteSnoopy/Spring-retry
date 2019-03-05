/**
 * @(#)ResponseCode.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.constant;

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