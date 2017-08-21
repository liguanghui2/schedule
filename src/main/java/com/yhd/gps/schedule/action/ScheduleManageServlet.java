package com.yhd.gps.schedule.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yhd.gps.busyservice.dao.DateSectionPriceHistoryDao;

/**
 * 定时器管理Servlet
 * @author lipengfei
 *
 */
public class ScheduleManageServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    private WebApplicationContext ctx = null;
    private final Log LOG = LogFactory.getLog(this.getClass());
    
    private DateSectionPriceHistoryDao dateSectionPriceHistoryDao;

    public ScheduleManageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.dateSectionPriceHistoryDao = (DateSectionPriceHistoryDao) ctx.getBean("dateSectionPriceHistoryDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        // 取入参
        String action = request.getParameter("action");
        try {
            if(action == null) {
                result = null;
            } else {
                // 查询,新增,修改,删除
                if("deleteDateSectionPrice".equals(action)) {
                    deleteDateSectionPrice();
                }
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
            result = "执行异常，请检查:" + e.getMessage();
        }

        String s = result == null ? "成功" : result;

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

    
    private void deleteDateSectionPrice() {
        List<Long> ids = dateSectionPriceHistoryDao.queryDateSectionPriceInfoByChannelId();
        LOG.error("###删除的Ids的size长度为：" + ids.size() + "###");
        dateSectionPriceHistoryDao.deleteDateSectionPriceHistoryByIds(ids);
    }
}
