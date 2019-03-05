/**
 * @(#)RetryService.java, 2018/9/4.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.service;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public interface RetryService {
    int minGoodsnum(int num) throws Exception;


    void recover(NullPointerException e);
}
