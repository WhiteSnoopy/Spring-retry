package com.netease.mail.retry.exception;

import java.io.Writer;

import org.apache.log4j.Logger;

import com.netease.mail.core.exception.ViewException;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * FREEMARKER异常处理
 *
 * @author chanyun
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

    private Logger logger = Logger.getLogger(FreemarkerExceptionHandler.class);

    @Override
    public void handleTemplateException(TemplateException arg0, Environment arg1, Writer arg2)
        throws TemplateException {
        logger.warn("freemarker error:" + arg0.getMessage());
        throw new ViewException("freemarker cause exception", arg0);
    }

}
