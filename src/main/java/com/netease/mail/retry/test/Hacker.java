/**
 * @(#)Hacker.java, 2018/9/5.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class Hacker implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("come in");
        methodProxy.invokeSuper(o, objects);
        System.out.println("come out");
        return null;
    }
}