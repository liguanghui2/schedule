package com.yhd.gps.schedule.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.yhd.gps.busyservice.dao.GpsScheduleWarningRuleDao;
import com.yhd.gps.schedule.common.JsonUtil;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.vo.GpsScheduleWarningRuleVo;
import com.yhd.gps.schedule.vo.ShardingIndexVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * 
 * 规则管理
 * @Author lipengcheng
 * @CreateTime 2017-3-7 上午10:21:33
 */
public class RuleManageServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    private WebApplicationContext ctx = null;
    private final Log LOG = LogFactory.getLog(this.getClass());

    private GpsScheduleWarningRuleDao gpsScheduleWarningRuleDao = null;
    private ShardingIndexDao shardingIndexDao = null;

    public RuleManageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.gpsScheduleWarningRuleDao = (GpsScheduleWarningRuleDao) ctx.getBean("gpsScheduleWarningRuleDao");
        this.shardingIndexDao = (ShardingIndexDao) ctx.getBean("shardingIndexDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        // 取入参
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String schema1 = request.getParameter("schema1");
        String sql1 = request.getParameter("sql1");
        String threshold = request.getParameter("threshold");
        String ruleType = request.getParameter("ruleType");
        String schema2 = request.getParameter("schema2");
        String sql2 = request.getParameter("sql2");
        String warningContent = request.getParameter("warningContent");
        String email = request.getParameter("email");
        String isValid = request.getParameter("isValid");
        String remark = request.getParameter("remark");
        String opType = request.getParameter("opType");
        String rows = request.getParameter("rows");
        String page = request.getParameter("page");

        // 查询,新增,修改,删除
        try {
            if(action == null) {
                result = null;
            } else {
                ParameterObject parameter = new ParameterObject(action, id, schema1, sql1, threshold, ruleType,
                    schema2, sql2, warningContent, email, isValid, remark, opType, rows, page);
                result = ruleManage(parameter);
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
            result = "执行异常，请检查:" + e.getMessage();
        }

        String s = result == null ? "" : result;

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

    private String ruleManage(ParameterObject parameter) throws Exception {
        String result = null;
        try {
            if("query".equals(parameter.action)) {
                result = queryRule(parameter);
            } else if("add".equals(parameter.action)) {
                result = addRule(parameter);
            } else if("update".equals(parameter.action)) {
                result = updateRule(parameter);
            } else if("delete".equals(parameter.action)) {
                result = deleteRule(parameter);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    // 查询规则
    private String queryRule(ParameterObject parameter) {
        String result = null;
        try {
            List<GpsScheduleWarningRuleVo> gpsScheduleWarningRuleVos = new ArrayList<GpsScheduleWarningRuleVo>();
            // 根据id查询
            if(StringUtils.isNotBlank(parameter.id)) {
                GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo = gpsScheduleWarningRuleDao
                        .getGpsScheduleWarningRuleVoById(Long.valueOf(parameter.id));
                if(null != gpsScheduleWarningRuleVo) {
                    gpsScheduleWarningRuleVos.add(gpsScheduleWarningRuleVo);
                }
            } else {
                // 全查询
                gpsScheduleWarningRuleVos = gpsScheduleWarningRuleDao.getAllGpsScheduleWarningRuleVoList();
            }
            int rows = Integer.parseInt(StringUtils.isNotBlank(parameter.rows) ? parameter.rows : "0");
            int page = Integer.parseInt(StringUtils.isNotBlank(parameter.page) ? parameter.page : "0");
            if(rows == 0 || page == 0) {
                page = 1;
                rows = 15;
            }
            int records = 0, total = 0;
            if(CollectionUtils.isNotEmpty(gpsScheduleWarningRuleVos)) {
                records = gpsScheduleWarningRuleVos.size();
            }
            total = records % rows == 0 ? records / rows : records / rows + 1;
            StringBuilder jsonResult = new StringBuilder("{\"gridData\":");
            jsonResult.append(JsonUtil.toJson(null == gpsScheduleWarningRuleVos ? "" : gpsScheduleWarningRuleVos))
                    .append(",\"page\":").append(page).append(",\"total\":").append(total).append(",\"records\":")
                    .append(records).append("}");
            result = jsonResult.toString();
        } catch (Exception e) {
            throw new BusyException("查询异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 新增规则
    private String addRule(ParameterObject parameter) throws Exception {
        String result = null;
        // 校验参数
        if(!parameter.checkParameters(parameter.schema1, parameter.sql1, parameter.threshold, parameter.ruleType,
                parameter.schema2, parameter.sql2, parameter.isValid, parameter.opType)) {
            return result;
        }
        try {
            // 根据表名查分片
            List<ShardingIndexVo> shardingVos = shardingIndexDao
                    .queryAllShardingByTableName(ScheduleConstants.SHARDING_NAME_RULE_EXECUTE);
            if(CollectionUtils.isEmpty(shardingVos)) {
                throw new BusyException("未配置切片，请先配置切片后重试！");
            }
            int shardingCount = shardingVos.size();
            // 按秒取模
            int shardingIndex = Calendar.getInstance().get(Calendar.SECOND) % shardingCount;
            GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo = new GpsScheduleWarningRuleVo();
            gpsScheduleWarningRuleVo.setSchema1(parameter.schema1);
            gpsScheduleWarningRuleVo.setSql1(parameter.sql1);
            gpsScheduleWarningRuleVo.setSchema2(parameter.schema2);
            gpsScheduleWarningRuleVo.setSql2(parameter.sql2);
            gpsScheduleWarningRuleVo.setRuleType(Integer.valueOf(parameter.ruleType));
            gpsScheduleWarningRuleVo.setThreshold(Integer.valueOf(parameter.threshold));
            boolean isValid = "true".equals(parameter.isValid);
            gpsScheduleWarningRuleVo.setValid(isValid);
            gpsScheduleWarningRuleVo.setShardingIndex(shardingIndex);
            gpsScheduleWarningRuleVo.setEmail(parameter.email);
            gpsScheduleWarningRuleVo.setWarningContent(parameter.warningContent);
            gpsScheduleWarningRuleVo.setRemark(parameter.remark);
            gpsScheduleWarningRuleVo.setOpType(Integer.valueOf(parameter.opType));
            Long insertResult = gpsScheduleWarningRuleDao.insertGpsScheduleWarningRuleVo(gpsScheduleWarningRuleVo);
            if(insertResult != null && insertResult > 0L) {
                result = "1";
            }
        } catch (Exception e) {
            throw new BusyException("新增规则异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 更新
    private String updateRule(ParameterObject parameter) throws Exception {
        String result = null;
        // 校验参数
        if(!parameter.checkParameters(parameter.id, parameter.ruleType, parameter.threshold, parameter.isValid)) {
            return result;
        }
        try {
            GpsScheduleWarningRuleVo gpsScheduleWarningRuleVo = new GpsScheduleWarningRuleVo();
            gpsScheduleWarningRuleVo.setId(Long.valueOf(parameter.id));
            gpsScheduleWarningRuleVo.setSchema1(parameter.schema1);
            gpsScheduleWarningRuleVo.setSql1(parameter.sql1);
            gpsScheduleWarningRuleVo.setSchema2(parameter.schema2);
            gpsScheduleWarningRuleVo.setSql2(parameter.sql2);
            gpsScheduleWarningRuleVo.setRuleType(Integer.valueOf(parameter.ruleType));
            gpsScheduleWarningRuleVo.setThreshold(Integer.valueOf(parameter.threshold));
            boolean isValid = "true".equals(parameter.isValid);
            gpsScheduleWarningRuleVo.setValid(isValid);
            gpsScheduleWarningRuleVo.setEmail(parameter.email);
            gpsScheduleWarningRuleVo.setWarningContent(parameter.warningContent);
            gpsScheduleWarningRuleVo.setRemark(parameter.remark);
            gpsScheduleWarningRuleVo.setOpType(Integer.valueOf(parameter.opType));
            result = String.valueOf(gpsScheduleWarningRuleDao.updateGpsScheduleWarningRuleVo(gpsScheduleWarningRuleVo));
        } catch (Exception e) {
            throw new BusyException("更新规则异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 删除
    private String deleteRule(ParameterObject parameter) {
        String result = null;
        // 校验参数
        if(!parameter.checkParameters(parameter.id)) {
            return result;
        }
        try {
            result = String.valueOf(gpsScheduleWarningRuleDao.deleteGpsScheduleWarningRuleVoById(Long
                    .valueOf(parameter.id)));
        } catch (Exception e) {
            throw new BusyException("删除规则异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    private static class ParameterObject {
        String action = null;
        String id = null;
        String schema1 = null;
        String sql1 = null;
        String threshold = null;
        String ruleType = null;
        String schema2 = null;
        String sql2 = null;
        String warningContent = null;
        String email = null;
        String isValid = null;
        String remark = null;
        String opType = null;
        String rows = "";
        String page = "";

        public ParameterObject(String action, String id, String schema1, String sql1, String threshold,
                               String ruleType, String schema2, String sql2, String warningContent, String email,
                               String isValid, String remark, String opType, String rows, String page) {
            this.action = action;
            this.id = id;
            this.schema1 = schema1;
            this.sql1 = sql1;
            this.threshold = threshold;
            this.ruleType = ruleType;
            this.schema2 = schema2;
            this.sql2 = sql2;
            this.warningContent = warningContent;
            this.email = email;
            this.isValid = isValid;
            this.remark = remark;
            this.opType = opType;
            this.rows = StringUtils.isEmpty(rows) ? "" : rows;
            this.page = StringUtils.isEmpty(page) ? "" : page;
        }

        // 校验参数
        public boolean checkParameters(String... args) {
            boolean result = true;
            for(String param : args) {
                if(StringUtils.isEmpty(param)) {
                    result = false;
                }
            }
            return result;
        }

    }
}
