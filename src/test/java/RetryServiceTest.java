import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.netease.mail.retry.service.RetryService;

/**
 * @author chanyun
 */
public class RetryServiceTest extends TestBase {

    @Autowired
    private RetryService retryService;

    @Test
    public void testRetry() throws Exception {
        retryService.minGoodsnum(-1);
    }
}
