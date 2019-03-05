/**
 * @(#)CgTest.java, 2018/9/5.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class CgTest {

    private static int MAX_RETRIES = 10;

    private static int MAX_WAIT_INTERVAL = 10;

    public static void main(String[] args) {
        /*
         * Programmer programmer = new Programmer(); Hacker hacker = new Hacker(); Enhancer enhancer = new Enhancer();
         * enhancer.setSuperclass(programmer.getClass()); enhancer.setCallback(hacker); Programmer proxy = (Programmer)
         * enhancer.create(); proxy.code();
         */
        System.out.println((long) Math.pow(2, 3));
    }

    /**
     * 返回2的指数，
     *
     * @param retryCount
     * @return
     */
    public static long getWaitTime(int retryCount) {
        long waitTime = (long) Math.pow(2, retryCount);
        return waitTime;
    }

    public static void doOperationAndWait() {
        int retries = 0;
        boolean retry = false;
        do {
            Results result = getAsyncOperationResult();
            switch (result) {
                case SUCCESS:
                    retry = false;
                    break;
                case NOT_READY:
                case NO_RESOURCE:
                case TOO_BUSY:
                case SERVER_ERROR:
                    retry = true;
                    break;
                default:
                    retry = false;
                    break;
            }
            if (retry) {
                try {
                    long waitTime = Math.min(getWaitTime(retries), MAX_WAIT_INTERVAL);
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (retry && retries++ < MAX_RETRIES);
    }

    public static Results getAsyncOperationResult() {
        return null;
    }

}
