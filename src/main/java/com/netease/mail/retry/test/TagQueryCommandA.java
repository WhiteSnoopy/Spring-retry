/**
 * @(#)TagQueryCommandA.java, 2019/1/6.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import java.util.Collections;
import java.util.List;

import com.netease.mail.retry.service.TagService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class TagQueryCommandA extends HystrixCommand<List<String>> {

    private TagService tagService;

    public TagQueryCommandA() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TagService"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("TagQueryCommand"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("TagServicePool"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)));
        this.tagService = ApplicationContextHelper.getBean(TagService.class);
    }

    @Override
    protected List<String> run() {
        return tagService.queryTag();
    }

    @Override
    protected List<String> getFallback() {
        return Collections.EMPTY_LIST;
    }
}
