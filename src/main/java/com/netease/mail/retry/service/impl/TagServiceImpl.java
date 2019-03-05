/**
 * @(#)TagServiceImpl.java, 2019/1/3.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.netease.mail.retry.service.TagService;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
@Service
public class TagServiceImpl implements TagService {

    public List<String> queryTag() {
        return Arrays.asList("Tag");
    }
}
