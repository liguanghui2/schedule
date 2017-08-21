package com.yhd.gps.schedule.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yhd.gps.busyservice.service.DataProcessScannerService;

/**
 * 
 * 扫描表服务
 * @Author lipengcheng
 * @CreateTime 2016-7-22 下午02:24:06
 */
public class ScannerManageServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    private WebApplicationContext ctx = null;
    private final Log LOG = LogFactory.getLog(this.getClass());

    private DataProcessScannerService dataProcessScannerService = null;

    public ScannerManageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.dataProcessScannerService = (DataProcessScannerService) ctx.getBean("dataProcessScannerService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        // 取入参
        String action = request.getParameter("action");
        String ruleId = request.getParameter("ruleId");

        try {
            if(action == null) {
                result = null;
            } else if("addRuleId".equals(action)) {
                Long scannerId = dataProcessScannerService.insertScannerByRuleId(Long.parseLong(ruleId));
                if(scannerId != null) {
                    result = "新增成功,id:" + scannerId.toString();
                }
            } else if("initScanner".equals(action)) {
                // 初始化扫描表
                dataProcessScannerService.initScanner4SamPromRule();
                result = "初始化扫描表完成。";
            }
        } catch (Exception e) {
            LOG.error("执行异常，请检查:", e);
            result = "执行异常，请检查:" + e.getMessage();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"result\":\"").append(result).append("\"}");
        String s = sb.toString();

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        response.setContentLength(s.getBytes("utf-8").length);
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        response.getWriter().append(s);
        response.getWriter().flush();
        response.getWriter().close();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
