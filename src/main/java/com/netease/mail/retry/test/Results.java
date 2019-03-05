/**
 * @(#)Results.java, 2018/9/7.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author chanyun(hzchenyun1 @ corp.netease.com)
 */
public enum Results {

    SUCCESS(0, "成功"),               //成功
    NOT_READY(1, "维护中"),         //维护中
    TOO_BUSY(2, "流控中"),            //流控中
    NO_RESOURCE(3, "没有资源"),    //没有资源
    SERVER_ERROR(4, "系统错误");    //系统错误
    private int code;

    private String desc;

    private static Map<Integer, Results> CODE_MAP = new HashMap<Integer, Results>();
    static {
        for (Results obj: Results.values()) {
            CODE_MAP.put(obj.code, obj);
        }
    }

    Results(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return this.desc;
    }

    @JsonCreator
    public static Results valueOf(int code) {
        Results obj = CODE_MAP.get(code);
        if (null == obj) {
            throw new IllegalArgumentException("unrecognized code: " + code);
        }
        return obj;
    }
}