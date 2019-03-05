/**
 * @(#)AjaxResult.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.meta;

import com.netease.mail.retry.constant.ResponseCode;

/**
 * ajax请求返回结果封装
 *
 * @author 王国云(wangguoyun@corp.netease.com)
 * @Date 2011-7-13
 */
public class AjaxResult extends JsonResult {
    /**
     *
     */
    private static final long serialVersionUID = 5451316194035153776L;

    public AjaxResult() {
        super();
    }

    public AjaxResult(ResponseCode responseCode) {
        super(responseCode);
    }

    public AjaxResult(ResponseCode responseCode, String errorCode) {
        super(responseCode, errorCode);
    }

    public AjaxResult(ResponseCode responseCode, String errorCode, Object obj) {
        super(responseCode, errorCode);
        setData(obj);
    }
}