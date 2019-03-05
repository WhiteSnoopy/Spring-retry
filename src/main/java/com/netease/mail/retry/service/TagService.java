/**
 * @(#)TagService.java, 2019/1/3.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.service;

import java.util.List;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public interface TagService {

    List<String> queryTag();
}
