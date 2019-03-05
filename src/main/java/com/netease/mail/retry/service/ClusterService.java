/**
 * @(#)ClusterService.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.service;

import java.util.Set;

import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.data.redis.connection.RedisClusterNode;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public interface ClusterService {

    /**
     * 获取集群信息
     */
    ClusterInfo clusterInfo();

    /**
     * 集群所有节点信息
     *
     * @return
     */
    Set<RedisClusterNode> clusterNodes();

    void nodesInfo();
}