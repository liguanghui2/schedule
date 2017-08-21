package com.yhd.gps;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Hikin Yao
 * @version 1.0
 */
public abstract class BaseServletTest extends BaseSpringWithUnitilsTest {
    protected WebApplicationContext webApplicationContext;
    protected ServletConfig servletConfig;
    protected ServletContext servletContext;
    protected WebApplicationContextUtils webApplicationContextUtils;
    protected HttpServletRequest mockRequest;
    protected HttpServletResponse mockResponse;

    protected abstract void init(ServletConfig servletConfig) throws ServletException;

    protected void assertExpectResult(String result) throws Exception {
        mockResponse.setCharacterEncoding("utf-8");
        mockResponse.setContentType("text/json");
        mockResponse.setContentLength(result.getBytes("utf-8").length);
        mockResponse.setHeader("Expires", "0");
        mockResponse.setHeader("Pragma", "No-cache");
        File file = new File("1");
        if(!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new FileOutputStream(file), true);
        expect(mockResponse.getWriter()).andReturn(pw).anyTimes();
        replay(mockResponse);
    }

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
        // 创建request和response的Mock
        mockRequest = createMock(HttpServletRequest.class);
        mockResponse = createMock(HttpServletResponse.class);
        servletConfig = createMock(ServletConfig.class);
        servletContext = createMock(ServletContext.class);
        expect(servletConfig.getServletContext()).andReturn(servletContext).anyTimes();
        webApplicationContext = createMock(WebApplicationContext.class);
        webApplicationContextUtils = createMock(WebApplicationContextUtils.class);
        expect(WebApplicationContextUtils.getWebApplicationContext(servletContext)).andReturn(webApplicationContext)
                .anyTimes();

        replay(servletConfig);
        replay(servletContext);
        replay(webApplicationContextUtils);

        init(servletConfig);
    }

    @After
    public void onTearDown() throws Exception {
        super.onTearDown();
        // 为了验证指定的调用行为确实发生了，要调用verify(mock)进行验证。
        // verify(mockRequest, mockResponse, servletConfig,
        // servletContext, webApplicationContext,
        // webApplicationContextUtils);
        reset(mockRequest, mockResponse, servletConfig, servletContext, webApplicationContext,
                webApplicationContextUtils);
    }

}
