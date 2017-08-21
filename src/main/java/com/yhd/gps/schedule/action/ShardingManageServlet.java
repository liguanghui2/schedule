package com.yhd.gps.schedule.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.yhd.gps.schedule.common.JsonUtil;
import com.yhd.gps.schedule.common.MiscConfigUtils;
import com.yhd.gps.schedule.dao.PriceBoardDataRecordDao;
import com.yhd.gps.schedule.dao.ShardingIndexDao;
import com.yhd.gps.schedule.service.ShardingIndexService;
import com.yhd.gps.schedule.vo.ShardingIndexVo;
import com.yihaodian.busy.exception.BusyException;

/**
 * 
 * 分片管理
 * @Author lipengcheng
 * @CreateTime 2016-7-22 下午01:23:45
 */
public class ShardingManageServlet extends HttpServlet {
    private static final long serialVersionUID = -1L;
    private WebApplicationContext ctx = null;
    private final Log LOG = LogFactory.getLog(this.getClass());

    private ShardingIndexDao shardingIndexDao = null;
    private ShardingIndexService shardingIndexService = null;
    private PriceBoardDataRecordDao priceBoardDataRecordDao = null;

    public ShardingManageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        this.shardingIndexDao = (ShardingIndexDao) ctx.getBean("shardingIndexDao");
        this.shardingIndexService = (ShardingIndexService) ctx.getBean("shardingIndexService");
        this.priceBoardDataRecordDao = (PriceBoardDataRecordDao)ctx.getBean("priceBoardDataRecordDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        // 取入参
        String action = request.getParameter("action");
        String input = request.getParameter("input");
        String inputId = request.getParameter("inputId");
        String inputTableName = request.getParameter("inputTableName");
        String inputShardingCount = request.getParameter("inputShardingCount");
        String inputAddShardingCount = request.getParameter("inputAddShardingCount");
        String rows = request.getParameter("rows");
        String page = request.getParameter("page");

        // 查询,新增,修改,删除
        try {
            if(action == null) {
                result = null;
            } else {
                ParameterObject parameter = new ParameterObject(action, input, inputId, inputTableName,
                    inputShardingCount, inputAddShardingCount, rows, page);
                result = shardingManage(parameter);
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

    private String shardingManage(ParameterObject parameter) throws Exception {
        String result = null;
        try {
            if("query".equals(parameter.action)) {
                result = querySharding(parameter);
            } else if("add".equals(parameter.action)) {
                result = addSharding(parameter);
            } else if("update".equals(parameter.action)) {
                result = updateSharding(parameter);
            } else if("delete".equals(parameter.action)) {
                result = deleteShardingIp(parameter);
            } else if("deleteShardingPhysics".equals(parameter.action)) {
                result = deleteShardingPhysics(parameter);
            }else if("deleteWeekNum".equals(parameter.action)) {
                result = deleteWeekNum(parameter);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    // 查询分片
    private String querySharding(ParameterObject parameter) {
        String result = null;
        try {
            List<ShardingIndexVo> shardingVos = null;
            Integer shardingCount = null;
            if(StringUtils.isNotEmpty(parameter.input)) {
                // 根据表名查分片
                shardingVos = shardingIndexDao.queryAllShardingByTableName(parameter.input);
            } else if(StringUtils.isNotEmpty(parameter.inputId)) {
                // 根据id查分片
                shardingVos = shardingIndexDao.queryShardingById(Long.parseLong(parameter.inputId));
                if(CollectionUtils.isNotEmpty(shardingVos)) {
                    shardingCount = MiscConfigUtils.getShardingCount(shardingVos.get(0).getTableName());
                }
            } else {
                // 全查询
                shardingVos = shardingIndexDao.queryAllSharding();
            }
            int rows = Integer.parseInt(StringUtils.isNotBlank(parameter.rows) ? parameter.rows : "0");
            int page = Integer.parseInt(StringUtils.isNotBlank(parameter.page) ? parameter.page : "0");
            if(rows == 0 || page == 0) {
                page = 1;
                rows = 15;
            }
            int records = 0, total = 0;
            records = shardingVos == null ? records : shardingVos.size();
            total = records % rows == 0 ? records / rows : records / rows + 1;
            StringBuilder jsonResult = new StringBuilder("{\"gridData\":");
            jsonResult.append(JsonUtil.toJson(null == shardingVos ? "" : shardingVos)).append(",\"shardingCount\":")
                    .append(null == shardingCount ? 0 : shardingCount).append(",\"page\":").append(page)
                    .append(",\"total\":").append(total).append(",\"records\":").append(records).append("}");
            result = jsonResult.toString();
        } catch (Exception e) {
            throw new BusyException("查询异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 新增分片
    private String addSharding(ParameterObject parameter) throws Exception {
        String result = null;
        // 校验参数
        if(!parameter.checkParameters(parameter.inputTableName, parameter.inputShardingCount)) {
            return result;
        }
        try {
            int shardingCount = Integer.parseInt(parameter.inputShardingCount);
            if(shardingCount < 1) {
                throw new BusyException("切片个数不能小于1");
            }
            // 根据表名查分片
            List<ShardingIndexVo> shardingVos = shardingIndexDao.queryAllShardingByTableName(parameter.inputTableName);
            if(CollectionUtils.isNotEmpty(shardingVos)) {
                throw new BusyException("待切片的数据表名已存在！");
            }
            shardingVos = new ArrayList<ShardingIndexVo>(shardingCount);
            for(int i = 0; i < shardingCount; i++) {
                ShardingIndexVo shardingVo = new ShardingIndexVo(parameter.inputTableName, i, 1, "");
                shardingVos.add(shardingVo);
            }
            result = String.valueOf(shardingIndexDao.batchInsertSharding(shardingVos));
        } catch (Exception e) {
            throw new BusyException("新增异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 扩容分片
    private String updateSharding(ParameterObject parameter) throws Exception {
        String result = null;
        // 校验参数
        if(!parameter.checkParameters(parameter.inputTableName, parameter.inputAddShardingCount)) {
            return result;
        }
        try {
            int addSize = Integer.parseInt(parameter.inputAddShardingCount);
            if(addSize < 1 || addSize > 127) {
                throw new BusyException("扩容切片个数不能小于1，不能大于127");
            }
            result = String.valueOf(shardingIndexService.addShardingEx(parameter.inputTableName, addSize));
        } catch (Exception e) {
            throw new BusyException("扩容异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }

    // 抹除分片ip
    private String deleteShardingIp(ParameterObject parameter) {
        String result = "1";
        try {
            List<ShardingIndexVo> shardingVos = shardingIndexDao.queryAllSharding();
            if(CollectionUtils.isEmpty(shardingVos)) {
                throw new BusyException("查不到切片");
            }
            Set<Long> shardingIds = new HashSet<Long>(shardingVos.size());
            for(ShardingIndexVo shardingVo : shardingVos) {
                if(StringUtils.isNotEmpty(shardingVo.getIp())) {
                    shardingIds.add(shardingVo.getId());
                }
            }
            if(CollectionUtils.isNotEmpty(shardingIds)) {
                result = String
                        .valueOf(shardingIndexDao.batchUpdateShardingValidById(new ArrayList<Long>(shardingIds)));
            }
        } catch (Exception e) {
            throw new BusyException("抹除ip异常:" + e.getMessage(), e.getCause());
        }
        return result;
    }
    
    //物理删除切片
    private String deleteShardingPhysics(ParameterObject parameter){
        String result=null;
        String id=parameter.inputId;
        if(StringUtils.isBlank(id)){
            throw new BusyException("入参异常！！");
        }
        try {
            Long shardingId=Long.parseLong(id);
            result = String.valueOf(shardingIndexDao.deleteShardingById(shardingId));
        } catch (Exception e) {
            throw new BusyException("删除切片异常！！");
        }
        return result;
    }
    
    private String deleteWeekNum(ParameterObject parameter){
        String result=null;
        String id=parameter.inputId;
        if(StringUtils.isBlank(id)){
            throw new BusyException("入参异常！！"); 
        }
        try {
            Integer weekNum = Integer.valueOf(id);
            result = String.valueOf(priceBoardDataRecordDao.deleteWeekPriceBoardByWeekNum(weekNum));
        } catch (Exception e) {
            throw new BusyException("删除周历史价格数据异常！！");
        }
        return result;
    }

    private static class ParameterObject {
        String action = null;
        String input = null;
        String inputId = null;
        String inputTableName = null;
        String inputShardingCount = null;
        String inputAddShardingCount = null;
        String rows = "";
        String page = "";

        public ParameterObject(String action, String input, String inputId, String inputTableName,
                               String inputShardingCount, String inputAddShardingCount, String rows, String page) {
            this.action = action;
            this.input = input;
            this.inputId = inputId;
            this.inputTableName = inputTableName;
            this.inputShardingCount = inputShardingCount;
            this.inputAddShardingCount = inputAddShardingCount;
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
