
/**
 * @(#)RetryServiceTest.java, 2018/9/4.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netease.mail.retry.service.RetryService;
import com.netease.mail.retry.service.impl.RetryS;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class RetryServiceTest extends TestBase {

    @Autowired
    private RetryS retryS;

    @Test
    public void testRetry() throws Exception {
        retryS.minGoodsnum(-1);
    }
}
