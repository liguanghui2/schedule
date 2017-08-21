package com.yhd.gps.schedule.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yhd.gps.busyservice.dao.BusyPmInfoDao;
import com.yhd.gps.busyservice.service.JDBookSyncService;
import com.yhd.gps.schedule.jd.JDBookSyncMessageConsumer;
import com.yhd.gps.schedule.vo.JDBookMessageVo;
import com.yihaodian.architecture.jumper.consumer.BackoutMessageException;
import com.yihaodian.busy.common.BusyConstants;
import com.yihaodian.busy.common.GPSUtils;

/**
 * 
 * 补偿数据服务
 * @Author lipengcheng
 * @CreateTime 2016-12-21 下午08:20:13
 */
public class CompensateServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    private WebApplicationContext ctx = null;
    private final Log LOG = LogFactory.getLog(this.getClass());

    private BusyPmInfoDao busyPmInfoDao;
    private JDBookSyncService jdBookSyncService;

    public CompensateServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.busyPmInfoDao = (BusyPmInfoDao) ctx.getBean("busyPmInfoDao");
        this.jdBookSyncService = (JDBookSyncService) ctx.getBean("jdBookSyncService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        // 取入参
        String action = request.getParameter("action");
        String pmIds = request.getParameter("pmIds");

        try {
            if(action == null) {
                result = null;
            } else if("compensateJDBookPrice".equals(action)) {
                if(StringUtils.isNotBlank(pmIds)) {
                    String pmIdsStr[] = pmIds.split(",");
                    if(pmIdsStr.length == 0 || pmIdsStr.length > BusyConstants.MAX_QUERY_SIZE) {
                        result = "没有输入pmId或者pmId数量大于" + BusyConstants.MAX_QUERY_SIZE + "个";
                    } else {
                        List<Long> pmIdList = new ArrayList<Long>(pmIdsStr.length);
                        for(String pmIdStr : pmIdsStr) {
                            Long pmId = Long.valueOf(pmIdStr);
                            pmIdList.add(pmId);
                        }
                        int resultCount = compensateJDBookPrice(pmIdList);
                        result = "同步成功" + resultCount + "条数据";
                    }
                }
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

    private int compensateJDBookPrice(List<Long> pmIds) throws BackoutMessageException {
        int result = 0;
        if(CollectionUtils.isEmpty(pmIds)) {
            return result;
        }
        // 根据pmId去抓数据
        List<JDBookMessageVo> messageVos = busyPmInfoDao.getJDBookSyncInfoByPmIds(pmIds);
        if(CollectionUtils.isEmpty(messageVos)) {
            return result;
        }
        String remark = "手工补偿同步京东图书价格";
        // 分批执行同步京东图书价格
        List<List<JDBookMessageVo>> splitJDBookMessageVos = GPSUtils.split(messageVos,
                JDBookSyncMessageConsumer.MAX_MSG_SIZE);
        for(List<JDBookMessageVo> jdBookMessageVos : splitJDBookMessageVos) {
            int modCount = jdBookSyncService.syncJDBookPrice(jdBookMessageVos, remark);
            result += modCount;
        }
        return result;
    }

}
