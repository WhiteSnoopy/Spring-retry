/**
 * @(#)JedisClusterTest.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * @author chanyun(hzchenyun1 @ corp.netease.com)
 */
public class JedisClusterTest extends TestBase {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private JedisClusterConnection jedisClusterConnection;

    @Test
    public void clusterTest() {
        Map<String, JedisPool> clusterMap = jedisCluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Test
    public void clusterConTest() {
        Set<RedisClusterNode> redisClusterNodeSet = jedisClusterConnection.clusterGetNodes();
        for(RedisClusterNode redisClusterNode : redisClusterNodeSet){
            System.out.println(redisClusterNode.getHost());
        }
    }

}