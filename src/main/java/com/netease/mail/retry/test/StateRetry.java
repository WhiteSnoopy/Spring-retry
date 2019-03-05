/**
 * @(#)StateRetry.java, 2018/9/5.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import java.util.Collections;

import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.RetryState;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class StateRetry {

    public static void main(String[] args) {

        RetryTemplate template = new RetryTemplate();
        //重试策略：次数重试策略
        RetryPolicy retryPolicy = new SimpleRetryPolicy(3,
            Collections.<Class<? extends Throwable>, Boolean>singletonMap(Exception.class, true));
        template.setRetryPolicy(retryPolicy);
        //退避策略：指数退避策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(100);
        backOffPolicy.setMaxInterval(3000);
        backOffPolicy.setMultiplier(2);
        backOffPolicy.setSleeper(new ThreadWaitSleeper());
        template.setBackOffPolicy(backOffPolicy);

        //当前状态的名称，当把状态放入缓存时，通过该key查询获取
        Object key = "mykey";
        //是否每次都重新生成上下文还是从缓存中查询，即全局模式（如熔断器策略时从缓存中查询）
        boolean isForceRefresh = true;
        //对DataAccessException进行回滚
        BinaryExceptionClassifier rollbackClassifier = new BinaryExceptionClassifier(
            Collections.<Class<? extends Throwable>>singleton(DataAccessException.class));
        RetryState state = new DefaultRetryState(key, isForceRefresh, rollbackClassifier);

        String result = template.execute(new RetryCallback<String, RuntimeException>() {
            @Override
            public String doWithRetry(RetryContext context) throws RuntimeException {
                System.out.println("retry count:" + context.getRetryCount());
                throw new TypeMismatchDataAccessException("");
            }
        }, new RecoveryCallback<String>() {
            @Override
            public String recover(RetryContext context) throws Exception {
                return "default";
            }
        }, null);

        System.out.println(result);
    }
}
