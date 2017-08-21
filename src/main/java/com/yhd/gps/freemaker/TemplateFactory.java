package com.yhd.gps.freemaker;

import java.io.IOException;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.yhd.gps.schedule.common.SpringBeanUtil;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 邮件内容模板生产工厂类
 * @Author lipengcheng
 * @CreateTime 2016-8-15 下午10:38:09
 */
public class TemplateFactory {
    // 模板生成配置
    private static FreeMarkerConfigurer conf;
    static {
        // 设置模板文件路径
        conf = (FreeMarkerConfigurer) SpringBeanUtil.getBean("freeMarkerConfigurer");
    }

    /**
     * 根据模板生成html
     * @param name 模板文件的名称
     * @param map 与模板内容转换对象
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateHtmlFromFtl(String name, Map<String, Object> map) throws IOException, TemplateException {
        Template t = conf.getConfiguration().getTemplate(name);
        return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
    }
}
