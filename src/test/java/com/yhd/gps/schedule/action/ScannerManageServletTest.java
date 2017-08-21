package com.yhd.gps.schedule.action;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.unitils.spring.annotation.SpringBeanByName;

import com.yhd.gps.BaseServletTest;
import com.yhd.gps.busyservice.service.impl.DataProcessScannerServiceImpl;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2016-7-22 下午05:25:55
 */
public class ScannerManageServletTest extends BaseServletTest {
    private ScannerManageServlet servlet;
    @SpringBeanByName
    private DataProcessScannerServiceImpl dataProcessScannerService;

    @Override
    protected void init(ServletConfig servletConfig) throws ServletException {
        servlet = PowerMockito.mock(ScannerManageServlet.class);
        expect(webApplicationContext.getBean("dataProcessScannerService")).andReturn(dataProcessScannerService)
                .anyTimes();
        replay(webApplicationContext);
        servlet.init(servletConfig);
    }

    @Test
    public void testDoGet001() {
        try {
            // 录制动作
            mockRequest.getHeader("x-forwarded-for");
            expectLastCall().andReturn("127.0.0.1");
            mockRequest.getHeader("Referer");
            expectLastCall().andReturn("");
            mockRequest.getParameter("action");
            expectLastCall().andReturn("addRuleId");
            mockRequest.getParameter("ruleId");
            expectLastCall().andReturn("123321198661");
            // 回放
            replay(mockRequest);
            // 期望结果
            String result = "{\"result\":\"新增成功,id:1234\"}";
            assertExpectResult(result);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
