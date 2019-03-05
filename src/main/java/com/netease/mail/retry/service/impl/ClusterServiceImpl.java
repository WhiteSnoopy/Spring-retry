/**
 * @(#)ClusterServiceImpl.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.service.impl;

import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.netease.mail.retry.service.ClusterService;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
@Service("clusterService")
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private JedisClusterConnection jedisClusterConnection;

    @Override
    public ClusterInfo clusterInfo() {
        return jedisClusterConnection.clusterGetClusterInfo();
    }

    @Override
    public Set<RedisClusterNode> clusterNodes() {
        Set<RedisClusterNode> redisClusterNodeSet = jedisClusterConnection.clusterGetNodes();
        return redisClusterNodeSet;
    }

    @Override
    public void nodesInfo() {
        Properties properties = jedisClusterConnection.info();
        System.out.println(JSONObject.toJSON(properties));
    }
}