/**
 * @(#)ClusterController.java, 2018/4/23.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.mail.retry.meta.AjaxResult;
import com.netease.mail.retry.service.ClusterService;
import com.netease.mail.retry.test.FileMonitor;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
@RequestMapping("/monitor")
@Controller
public class ClusterController extends BaseAjaxController {

    @Autowired
    private ClusterService clusterService;

    /**
     * 集群信息
     *
     * @return
     */
    @RequestMapping("/cluster/info")
    @ResponseBody
    public AjaxResult clusterInfo() {
        return initSuccessResult(clusterService.clusterInfo());
    }

    /**
     * 集群节点信息
     *
     * @return
     */
    @RequestMapping("/cluster/nodes")
    @ResponseBody
    public AjaxResult clusterNodes() {
        return initSuccessResult(clusterService.clusterNodes());
    }

    /**
     * 节点信息
     *
     * @return
     */
    @RequestMapping("/nodes/info")
    @ResponseBody
    public AjaxResult nodesInfo() {
        clusterService.nodesInfo();
        return initSuccessResult();
    }

    @RequestMapping("/test")
    @ResponseBody
    public AjaxResult test() {
        System.out.println(FileMonitor.get("a"));
        return initSuccessResult();
    }
}
