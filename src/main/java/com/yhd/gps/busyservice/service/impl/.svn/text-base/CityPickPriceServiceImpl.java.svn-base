package com.yhd.gps.busyservice.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.ServletContextAware;

import com.yhd.gps.busyservice.service.CityPickPriceService;
import com.yhd.gps.busyservice.util.ScheduleExcelUtil;
import com.yhd.gps.schedule.common.ScheduleConstants;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.pss.spi.common.vo.Response;
import com.yhd.pss.spi.merchant.vo.FullFieldsMerchant;
import com.yhd.pss.spi.merchant.vo.input.QueryMerchantByIdRequest;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.EmailConfigVo;
import com.yihaodian.backend.price.cityPick.vo.EmailTypeVo;
import com.yihaodian.backend.price.cityPick.vo.QuerySchedulePriceRequest;
import com.yihaodian.backend.price.cityPick.vo.SchedulePriceInfo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.busy.common.GPSUtils;
import com.yihaodian.front.busystock.client.BusyStockClientUtil;
import com.yihaodian.front.busystock.vo.BSProductPromRuleVo;
import com.yihaodian.pss.client.PssClient;
import com.yihaodian.ycm.dcenter.util.mailSender.SendUtil;

/**
 * Created by zhangshuqiang on 2017/5/15.
 */
public class CityPickPriceServiceImpl implements CityPickPriceService, ServletContextAware {

    private static final Log logger = LogFactory.getLog(CityPickPriceServiceImpl.class);

    // 价格后台远程Service
    private static final CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();

    private static final int QUERY_SCHEDULE_PRICE_PAGE_LIMIT = 3000;

    private ServletContext servletContext;

    @Override
    public List<SchedulePriceInfo> querySchedulePriceList(Long scheduleId, Long merchantId, List<Integer> priceStatus) {

        List<SchedulePriceInfo> result = new ArrayList<SchedulePriceInfo>();

        QuerySchedulePriceRequest request = new QuerySchedulePriceRequest();
        request.setPriceScheduleId(scheduleId);
        request.setPriceStatus(priceStatus);
        request.setMerchantId(merchantId);
        request.setIsManual(0);
        List<Integer> productSourceTypeList = new ArrayList<Integer>();
        productSourceTypeList.add(ScheduleConstants.AUTO_CONFIRM_PRICE_SCHEDULE_SOURCE_TYPE);
        request.setProductSourceType(productSourceTypeList);

        ResultDto<Long> countResult = cityPickPriceApp.countSchedulePriceList(request);
        if (countResult != null && ResultDto.STATUS_SUCCESS.equals(
                countResult.getStatus())) {
            Long pageCount = countResult.getResult();

            long count = pageCount / QUERY_SCHEDULE_PRICE_PAGE_LIMIT + ((pageCount % QUERY_SCHEDULE_PRICE_PAGE_LIMIT == 0) ? 0 : 1);
            request.setPageSize(Long.valueOf(QUERY_SCHEDULE_PRICE_PAGE_LIMIT));
            for (long page = 1; page <= count; page++) {
                request.setCurrentPage(page);
                //此处分页查询
                ResultDto<List<SchedulePriceInfo>> listResult = cityPickPriceApp.querySchedulePriceList(request);

                if (listResult != null && ResultDto.STATUS_SUCCESS.equals(
                        countResult.getStatus())) {
                    List<SchedulePriceInfo> schedulePriceInfoList = listResult.getResult();
                    if (CollectionUtils.isNotEmpty(schedulePriceInfoList)) {
                        result.addAll(schedulePriceInfoList);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void sendCityPickPriceEmail(ScheduleVo scheduleVo, Integer priceStatus) {
        //查询接口该用户是否配置了提醒邮件后发送邮件
        EmailTypeVo emailTypeVo = findAllEmailByType(ScheduleConstants.AUTO_PRICE_CONFIRM_EMAIL_TYPE);
        if (emailTypeVo != null) {
            EmailConfigVo emailConfigVo = findEmailConfigByMerchantId(scheduleVo.getMerchantId(), emailTypeVo.getId());

            if (emailConfigVo != null) {
                List<Integer> priceStatusList = new ArrayList<Integer>();
                priceStatusList.add(priceStatus);
                List<SchedulePriceInfo> schedulePriceInfoList = querySchedulePriceList(scheduleVo.getId(), scheduleVo.getMerchantId(), priceStatusList);

                if (CollectionUtils.isNotEmpty(schedulePriceInfoList)) {
                    String merchantName = queryMerchantName(scheduleVo.getMerchantId());

                    //fullfill email content
                    StringBuffer priceScheduleTime = new StringBuffer();
                    priceScheduleTime.append(ScheduleDateUtils.format(scheduleVo.getStartTime())).append("~")
                            .append(ScheduleDateUtils.format(scheduleVo.getEndTime()));
                    String content = String.format(emailTypeVo.getEmailContent(), new Object[]{merchantName, scheduleVo.getName(), priceScheduleTime.toString()});
                    emailTypeVo.setEmailContent(content);
                    //fullfill email subject
                    String subject = String.format(emailTypeVo.getEmailSubject(), merchantName);
                    emailTypeVo.setEmailSubject(subject);

                    doCoreSendPriceEmail(emailConfigVo.getEmailAddress(), emailTypeVo, schedulePriceInfoList,
                            emailTypeVo.getAttachmentName(), merchantName);
                }
            }
        }
    }

    @Override
    public EmailTypeVo findAllEmailByType(Integer emailType) {
        EmailTypeVo emailTypeVo = null;
        ResultDto<List<EmailTypeVo>> emailTypeResult = cityPickPriceApp.findAllEmailTypes();
        if (emailTypeResult != null && ResultDto.STATUS_SUCCESS.equals(
                emailTypeResult.getStatus())) {
            List<EmailTypeVo> emailTypeVos = emailTypeResult.getResult();
            if (CollectionUtils.isNotEmpty(emailTypeVos)) {
                for (EmailTypeVo typeVo : emailTypeVos) {
                    if (typeVo.getEmailType().equals(emailType)) {
                        emailTypeVo = typeVo;
                        break;
                    }
                }
            }
        }

        return emailTypeVo;
    }

    private EmailConfigVo findEmailConfigByMerchantId(Long merchantId, Long emailTypeId) {
        EmailConfigVo emailConfigVo = null;
        ResultDto<List<EmailConfigVo>> emailConfigResult = cityPickPriceApp.findEmailConfigByMerchantId(merchantId, emailTypeId);
        if (emailConfigResult != null && ResultDto.STATUS_SUCCESS.equals(
                emailConfigResult.getStatus())) {
            List<EmailConfigVo> emailConfigVos = emailConfigResult.getResult();
            if (CollectionUtils.isNotEmpty(emailConfigVos)) {
                emailConfigVo = emailConfigVos.get(0);
            }
        }

        return emailConfigVo;
    }

    /**
     * 核心发送自动定价邮件逻辑
     *
     * @param emailTo
     * @param emailTypeVo
     * @param schedulePriceInfoList
     * @param fileName
     * @param merchantName
     */
    public void doCoreSendPriceEmail(String emailTo, EmailTypeVo emailTypeVo, List<SchedulePriceInfo> schedulePriceInfoList, String fileName, String merchantName) {

        File file = null;
        try {
            String timeStr = String.valueOf(System.currentTimeMillis());
            fileName = fileName.concat("-").concat(timeStr).concat(".xls");

            //导出至文件中
            file = this.exportCityPickPrice(schedulePriceInfoList, fileName, merchantName);
            if (file != null) {
                List<File> fileList = new ArrayList<File>();
                fileList.add(file);
                String result = SendUtil.sendInnerEmailWithSenderNameAndAttachment(emailTo, emailTypeVo.getEmailContent(), emailTypeVo.getEmailSubject(),
                        emailTypeVo.getEmailTypeName(), fileList);
                if (StringUtils.isEmpty(result) || !result.equals("00")) {
                    logger.error("发送邮件失败,状态码" + result + ",收件人:" + emailTo + "附件大小:" + schedulePriceInfoList.size());
                }
            }
        } catch (Exception e) {
            logger.error("doSendCityPickPriceEmail", e);
        } finally {
            if (file != null) {
                try {
                    file.delete();
                } catch (Exception ex) {
                    logger.error("file.delete", ex);
                }
            }
        }
    }

    /**
     * 导出定价到excel, 主体逻辑拷贝价格后台相关代码
     *
     * @param schedulePriceInfoList
     * @param fileName
     * @param merchantName
     * @return
     */
    public File exportCityPickPrice(List<SchedulePriceInfo> schedulePriceInfoList, String fileName, String merchantName) {

        HSSFWorkbook wb = new HSSFWorkbook();
        List<List<SchedulePriceInfo>> subLists = GPSUtils.split(schedulePriceInfoList, ScheduleConstants.MAX_ITEM_EVERY_EXCEL);

        int sheetPage = 1;

        for (List<SchedulePriceInfo> subList : subLists) {
            List<Long> scheduleIds = new ArrayList<Long>();
            Map<Long, ScheduleVo> scheduleMap = new HashMap<Long, ScheduleVo>();

            // 遍历 便于获取信息
            for (SchedulePriceInfo schedulePriceInfo : subList) {
                if (schedulePriceInfo == null) {
                    continue;
                }

                scheduleIds.add(schedulePriceInfo.getPriceScheduleId());
            }

            ResultDto<Map<Long, ScheduleVo>> resMap = cityPickPriceApp.getCityPickPriceScheduleByIds(scheduleIds);
            if (ResultDto.STATUS_SUCCESS.equals(resMap.getStatus())) {
                scheduleMap = resMap.getResult();
            }

            int index = 1;
            HSSFSheet sheet = ScheduleExcelUtil.createCityPickSheet(wb, sheetPage++);
            for (SchedulePriceInfo schedulePriceInfo : subList) {
                if (schedulePriceInfo == null) {
                    continue;
                }
                Long scheduleId = schedulePriceInfo.getPriceScheduleId();

                HSSFRow newRow = sheet.createRow(index);

                // 获取档期信息
                ScheduleVo scheduleVo = scheduleMap.get(scheduleId);
                if (scheduleVo != null) {
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.SCHEDULE_CODE, scheduleVo.getCode());
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.SCHEDULE_NAME, scheduleVo.getName());
                    Date startTime = scheduleVo.getStartTime();
                    Date endTime = scheduleVo.getEndTime();
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.SCHEDULE_TIME,
                            ScheduleDateUtils.format(startTime) + "~" + ScheduleDateUtils.format(endTime));
                }

                if (StringUtils.isNotEmpty(merchantName)) {
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.MERCHANT, merchantName);
                }

                // 设置产品编码和名称
                ScheduleExcelUtil.createCell(newRow, ScheduleConstants.PRODUCT_CODE, schedulePriceInfo.getProductCode());
                ScheduleExcelUtil.createCell(newRow, ScheduleConstants.PRODUCT_NAME, schedulePriceInfo.getProductName());

                // 0-5009-5034-5043:美容护理-缤纷彩妆-睫毛膏/睫毛增长液
                String searchName = schedulePriceInfo.getCategorySearchName();
                if (searchName != null) {
                    String[] strArr = searchName.split(":");
                    String backStr = strArr[1];
                    String[] searStr = backStr.split("-");
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.PRODUCT_CATEGORY, searStr[searStr.length - 1]);
                }

                ScheduleExcelUtil.createCell(newRow, ScheduleConstants.SUGGES_PRICE, schedulePriceInfo.getAdvicedPrice());
                ScheduleExcelUtil.createCell(newRow, ScheduleConstants.ACTIVE_PRICE, schedulePriceInfo.getActivePrice());

                // 设置当前促销Id和促销名称
                Long ruleId = schedulePriceInfo.getPromRuleId();
                if (ruleId != null) {
                    ScheduleExcelUtil.createCell(newRow, ScheduleConstants.CURRENT_PROM_ID, ruleId);
                    try{
                        BSProductPromRuleVo bsProductPromRuleVo = BusyStockClientUtil.getBusyPriceFacadeService().getProductPromRuleVoByRuleId(ruleId);
                        if (bsProductPromRuleVo != null) {
                            ScheduleExcelUtil.createCell(newRow, ScheduleConstants.PROM_NAME, bsProductPromRuleVo.getPromName());
                        }
                    } catch (Exception ex){
                        logger.error("BusyPriceFacadeService.getProductPromRuleVoByRuleId", ex);
                    }

                }
                index++;
            }
        }

        //拼接导出excel路径
        String basePath = servletContext.getRealPath("/") + "/upload/";
        String filePath = basePath.concat(fileName);
        return ScheduleExcelUtil.exportXslFile(wb, filePath);
    }

    private String queryMerchantName(Long merchantId) {
        String merchantName = null;
        QueryMerchantByIdRequest merchantByIdRequest = new QueryMerchantByIdRequest();
        merchantByIdRequest.setMerchantId(merchantId);
        try {
            Response<FullFieldsMerchant> remoteResult = PssClient.getInstance(ScheduleConstants.PSS_TIME_OUT,ScheduleConstants.GROUP_NAME).getQueryMerchantRemoteService().
                    queryMerchantById(merchantByIdRequest);
            if (remoteResult != null && remoteResult.getResult() != null) {
                FullFieldsMerchant fullFieldsMerchant = remoteResult.getResult();
                merchantName = fullFieldsMerchant.getMerchantName();
            }
        } catch (Exception ex) {
            logger.error("queryMapMerchantInfo", ex);
        }

        return merchantName;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
