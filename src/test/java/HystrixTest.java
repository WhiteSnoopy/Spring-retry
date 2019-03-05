
/**
 * @(#)HystrixTest.java, 2018/12/19.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.List;

import org.junit.Test;

import com.netease.mail.retry.test.TagQueryCommand;

/**
 * @author chanyun(hzchenyun1@corp.netease.com)
 */
public class HystrixTest extends TestBase {

    @Test
    public void test() {
        List<String> result = new TagQueryCommand().execute();
        System.out.println(result);
    }
}
