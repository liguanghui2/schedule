package com.yhd.gps.schedule.action;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhd.gps.BaseServletTest;
import com.yhd.gps.schedule.dao.ShardingIndexDao;

/**
 * 
 * ---- 请加注释 ------
 * @Author lipengcheng
 * @CreateTime 2016-7-22 下午05:25:55
 */
public class ShardingManageServletTest extends BaseServletTest {
    private ShardingManageServlet servlet;
    @Autowired
    private ShardingIndexDao shardingIndexDao;

    @Override
    protected void init(ServletConfig servletConfig) throws ServletException {
        servlet = PowerMockito.mock(ShardingManageServlet.class);
        expect(webApplicationContext.getBean("shardingIndexDao")).andReturn(shardingIndexDao).anyTimes();
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
            expectLastCall().andReturn("query");
            mockRequest.getParameter("input");
            expectLastCall().andReturn("PRODUCT_PROM_RULE");
            mockRequest.getParameter("inputId");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputTableName");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputShardingIndex");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputIsValid");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputIp");
            expectLastCall().andReturn("");
            mockRequest.getParameter("rows");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("page");
            expectLastCall().andReturn("1");
            // 回放
            replay(mockRequest);
            // 期望结果
            String result = "{\"gridData\":[{\"id\":1,\"tableName\":\"PRODUCT_PROM_RULE\",\"shardingIndex\":0,\"isValid\":1,\"ip\":\"\",\"updateTime\":\"2016-07-21 00:00:00\"}],\"page\":1,\"total\":1,\"records\":1}";
            assertExpectResult(result);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGet002() {
        try {
            // 录制动作
            mockRequest.getHeader("x-forwarded-for");
            expectLastCall().andReturn("127.0.0.1");
            mockRequest.getHeader("Referer");
            expectLastCall().andReturn("");
            mockRequest.getParameter("action");
            expectLastCall().andReturn("add");
            mockRequest.getParameter("input");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputId");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputTableName");
            expectLastCall().andReturn("PRODUCT_PROM_RULE");
            mockRequest.getParameter("inputShardingIndex");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIsValid");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIp");
            expectLastCall().andReturn("");
            mockRequest.getParameter("rows");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("page");
            expectLastCall().andReturn("1");
            // 回放
            replay(mockRequest);
            // 期望结果
            String result = "1";
            assertExpectResult(result);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGet003() {
        try {
            // 录制动作
            mockRequest.getHeader("x-forwarded-for");
            expectLastCall().andReturn("127.0.0.1");
            mockRequest.getHeader("Referer");
            expectLastCall().andReturn("");
            mockRequest.getParameter("action");
            expectLastCall().andReturn("update");
            mockRequest.getParameter("input");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputId");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputTableName");
            expectLastCall().andReturn("PRODUCT_PROM_RULE");
            mockRequest.getParameter("inputShardingIndex");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIsValid");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIp");
            expectLastCall().andReturn("");
            mockRequest.getParameter("rows");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("page");
            expectLastCall().andReturn("1");
            // 回放
            replay(mockRequest);
            // 期望结果
            String result = "1";
            assertExpectResult(result);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGet004() {
        try {
            // 录制动作
            mockRequest.getHeader("x-forwarded-for");
            expectLastCall().andReturn("127.0.0.1");
            mockRequest.getHeader("Referer");
            expectLastCall().andReturn("");
            mockRequest.getParameter("action");
            expectLastCall().andReturn("delete");
            mockRequest.getParameter("input");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputId");
            expectLastCall().andReturn("");
            mockRequest.getParameter("inputTableName");
            expectLastCall().andReturn("PRODUCT_PROM_RULE");
            mockRequest.getParameter("inputShardingIndex");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIsValid");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("inputIp");
            expectLastCall().andReturn("");
            mockRequest.getParameter("rows");
            expectLastCall().andReturn("1");
            mockRequest.getParameter("page");
            expectLastCall().andReturn("1");
            // 回放
            replay(mockRequest);
            // 期望结果
            String result = "1";
            assertExpectResult(result);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGet005() {
        try {
            // 录制动作
            mockRequest.getHeader("x-forwarded-for");
            expectLastCall().andReturn("127.0.0.2");
            mockRequest.getHeader("Referer");
            expectLastCall().andReturn("");
            mockResponse.sendRedirect("/error_404.html");
            // 回放
            replay(mockRequest);
            replay(mockResponse);
            // 开始测试Servlet的doGet方法
            servlet.doGet(mockRequest, mockResponse);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
