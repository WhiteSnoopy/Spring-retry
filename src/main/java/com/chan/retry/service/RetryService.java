package com.chan.retry.service;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * @author chanyun
 */
@Service("retryService")
@EnableRetry
public class RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final int totalNum = 100000;

    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public int minGoodsnum(int num) {
        logger.info("减库存开始" + System.currentTimeMillis());
        if (num <= 0) {
            throw new RuntimeException("rpc调用异常");
        }
        logger.info("减库存执行结束" + System.currentTimeMillis());
        return totalNum - num;
    }

    @Recover
    public int recover1(RuntimeException e) {
        logger.info("兜底回调");
        return 1;
    }

    public static void main(String[] args) {

        final RetryTemplate retryTemplate = new RetryTemplate();
        final SimpleRetryPolicy policy = new SimpleRetryPolicy(3,
            Collections.<Class<? extends Throwable>, Boolean>singletonMap(Exception.class, true));
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100);
        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setListeners(new RetryListener[] {});

        final RetryCallback<Object, Exception> retryCallback = new RetryCallback<Object, Exception>() {
            @Override
            public Object doWithRetry(RetryContext context) throws Exception {
                System.out.println("do some thing");
                context.setAttribute("key", "value");
                System.out.println(context.getRetryCount());
                throw new Exception("exception");
            }
        };

        final RecoveryCallback<Object> recoveryCallback = new RecoveryCallback<Object>() {
            @Override
            public Object recover(RetryContext context) throws Exception {
                System.out.println("do recovery operation");
                System.out.println(context.getAttribute("key"));
                return null;
            }
        };

        try {
            final Object result = retryTemplate.execute(retryCallback, recoveryCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
