/**
 * @(#)FileMonitor.java, 2019/1/6.
 * <p/>
 * Copyright 2019 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class FileMonitor {

    private static WatchService watchService;

    private static String filename;

    private static Properties properties;

    private static ClassPathResource resource;

    static {

        try {
            filename = "xxxxxx.properties";
            resource = new ClassPathResource(filename);
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(resource.getFile().getParent()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //启动一个线程监听内容变化，并重新载入配置
        Thread watchThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        WatchKey watchKey = watchService.take();
                        for (WatchEvent event: watchKey.pollEvents()) {
                            if (Objects.equals(event.context().toString(), filename)) {
                                properties = PropertiesLoaderUtils.loadProperties(resource);
                                break;
                            }
                            watchKey.reset();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            ;
        };

        //设置成守护进程
        watchThread.setDaemon(true);
        watchThread.start();

        //当服务器进程关闭时把监听线程close掉
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    watchService.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static String get(String key) {
        return properties.getProperty(key, "");
    }
}
