package com.chan.retry.service;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.sun.istack.internal.Nullable;

/**
 * @author chanyun
 */
public class GuavaRetry {

    private static final Logger logger = LoggerFactory.getLogger(GuavaRetry.class);

    private static <T> T run(Retryer<T> retryer, Callable<T> callable) {
        try {
            return retryer.call(callable);
        } catch (RetryException | ExecutionException e) {}
        return null;
    }

    private static Callable<String> callableWithResult() {
        return new Callable<String>() {
            int counter = 0;

            public String call() throws Exception {
                counter++;
                logger.info("do sth : {}", counter);
                if (counter < 5) {
                    throw new RuntimeException();
                }
                return "good";
            }
        };
    }

    private static RetryListener myRetryListener() {
        return new RetryListener() {
            @Override
            public <T> void onRetry(Attempt<T> attempt) {
                // 第几次重试,(注意:第一次重试其实是第一次调用)
                logger.info("[retry]time=" + attempt.getAttemptNumber());

                // 距离第一次重试的延迟
                logger.info(",delay=" + attempt.getDelaySinceFirstAttempt());

                // 重试结果: 是异常终止, 还是正常返回
                logger.info(",hasException=" + attempt.hasException());
                logger.info(",hasResult=" + attempt.hasResult());

                // 是什么原因导致异常
                if (attempt.hasException()) {
                    logger.info(",causeBy=" + attempt.getExceptionCause().toString());
                } else {
                    // 正常返回时的结果
                    logger.info(",result=" + attempt.getResult());
                }

                // 增加了额外的异常处理代码
                try {
                    T result = attempt.get();
                    logger.info(",rude get=" + result);
                } catch (ExecutionException e) {
                    logger.error("this attempt produce exception." + e.getCause().toString());
                }
            }
        };
    }

    private static RetryListener myRetryListener2() {
        return new RetryListener() {
            @Override
            public <T> void onRetry(Attempt<T> attempt) {
                logger.info("myRetryListener2 : [retry]time=" + attempt.getAttemptNumber());
            }
        };
    }

    private static <T> T runWithFixRetryAndListener(Callable<T> callable) {
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder().retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.exceptionWait(NullPointerException.class, itsFunction(NullPointerException.class)))
            .withStopStrategy(StopStrategies.neverStop()).withRetryListener(myRetryListener())
            .withRetryListener(myRetryListener2()).build();
        return run(retryer, callable);
    }

    // 根据不同异常,等待不同时间后重试
    private static <T extends Throwable> Function<T, Long> itsFunction(Class<T> exceptionClass) {
        Function<T, Long> result = new Function<T, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable T input) {
                if (input instanceof NullPointerException) {
                    return 1 * 1000L;
                } else if (input instanceof IOException) {
                    return 2 * 1000L;
                } else if (input instanceof ArithmeticException) {
                    return 3 * 1000L;
                } else if (input instanceof IllegalStateException) {
                    return 4 * 1000L;
                } else if (input instanceof IndexOutOfBoundsException) {
                    return 5 * 1000L;
                } else {
                    return 0L;
                }
            }
        };
        return result;
    }


/*    public static void main(String[] args) {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder().retryIfRuntimeException()
            .withStopStrategy(StopStrategies.neverStop())
            .withWaitStrategy(WaitStrategies.incrementingWait(200, TimeUnit.MILLISECONDS, 100, TimeUnit.MILLISECONDS))
            .build();
        System.out.println(run(retryer, callableWithResult()));

        runWithFixRetryAndListener(callableWithResult());
    }*/

    public static void main(String[] args) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // do something useful here
                throw new RuntimeException();
            }
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.fixedWait(10, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
