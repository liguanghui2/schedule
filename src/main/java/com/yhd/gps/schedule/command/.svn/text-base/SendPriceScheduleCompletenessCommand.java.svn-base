package com.yhd.gps.schedule.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;

import com.yhd.gps.schedule.common.Command;
import com.yhd.gps.schedule.common.CommonUtils;
import com.yhd.gps.schedule.common.ExecutorManager;
import com.yhd.gps.schedule.common.ScheduleDateUtils;
import com.yhd.schedule.sharding.exec.ExecResult;
import com.yhd.schedule.sharding.exec.ShardingDataExecCommand;
import com.yihaodian.backend.price.base.ResultDto;
import com.yihaodian.backend.price.cityPick.application.CityPickPriceApp;
import com.yihaodian.backend.price.cityPick.vo.EmailConfigVo;
import com.yihaodian.backend.price.cityPick.vo.EmailTypeVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleQueryVo;
import com.yihaodian.backend.price.cityPick.vo.ScheduleVo;
import com.yihaodian.backend.price.common.BackendPriceHedwigServiceFactory;
import com.yihaodian.ycm.dcenter.util.mailSender.SendUtil;

/**
 * ---- 请加注释 ------
 * @Author shengxu1
 * @CreateTime 2017-5-16 下午04:45:41
 */
public class SendPriceScheduleCompletenessCommand extends ShardingDataExecCommand<Integer, List<ScheduleVo>> {
    
    private static final CityPickPriceApp cityPickPriceApp = BackendPriceHedwigServiceFactory.getCityPickPriceApp();
    
    public SendPriceScheduleCompletenessCommand(int shardingIndex, CountDownLatch finishSignal, String bussinessType) {
        super(shardingIndex, finishSignal, bussinessType);
    }

    @Override
    public List<ScheduleVo> fetchBussinessDataes(int shardingIndex) {
        List<ScheduleVo> schedules = new ArrayList<ScheduleVo>();
        //  根据分片获取配合 了"检查档期完整性邮件警告检查"的商家
        ResultDto<List<EmailConfigVo>> res = cityPickPriceApp.findEmailConfigByShardingIndex(shardingIndex, 1);
        if(res.getStatus() == 0){
            return schedules;
        }
        Set<Long> merchantIds = new HashSet<Long>();
        List<EmailConfigVo> emailConfigVos = res.getResult();
        if(CollectionUtils.isNotEmpty(emailConfigVos)){
            for(EmailConfigVo emailConfigVo : emailConfigVos){
                if(emailConfigVo == null){
                    continue;
                }
                Long merchantId = emailConfigVo.getMerchantId();
                merchantIds.add(merchantId);
            }
        }
        
        //  查询8天之内将开始且状态为新建的档期
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        List<Integer> status = new ArrayList<Integer>();
        status.add(1);
        scheduleQueryVo.setStatus(status);
        scheduleQueryVo.setMerchantIds(new ArrayList<Long>(merchantIds));
        Calendar calendar = Calendar.getInstance();
        scheduleQueryVo.setStartTime(calendar.getTime());
        calendar.add(Calendar.DATE, 8);
        scheduleQueryVo.setEndTime(calendar.getTime());
        ResultDto<List<ScheduleVo>> scheduleRes = cityPickPriceApp.getCityPickPriceSchedule4Schedule(scheduleQueryVo);
        if(scheduleRes.getStatus() == 0){
            return schedules;
        }
        return scheduleRes.getResult();
    }

    @Override
    public ExecResult<Integer> doWork(List<ScheduleVo> scheduleVos) {
        String emailContent = "";
        String emailSubject = "";
        Set<Long> dataIds = Collections.emptySet();
        Integer result = 0;
        if(CollectionUtils.isEmpty(scheduleVos)) {
            return new ExecResult<Integer>(result, dataIds);
        }
        
        //  获取邮件内容和主题
        ResultDto<List<EmailTypeVo>> typeRes = cityPickPriceApp.findAllEmailTypes();
        if(typeRes.getStatus() == 0){
            return new ExecResult<Integer>(result, dataIds);
        }
        List<EmailTypeVo> typeVos = typeRes.getResult();
        if(CollectionUtils.isNotEmpty(typeVos)){
            for(EmailTypeVo emailTypeVo : typeVos){
                if(emailTypeVo == null || emailTypeVo.getEmailType() != 1){
                    continue;
                }
                emailContent = emailTypeVo.getEmailContent();
                emailSubject = emailTypeVo.getEmailSubject();
                break;
            }
        }
        
        final String content = emailContent;
        final String subject = emailSubject;
        for(final ScheduleVo scheduleVo : scheduleVos){
            if(scheduleVo == null){
                continue;
            }
            //发送邮件
            ExecutorManager.executeOnly(new Command<Object>() {
                @Override
                public Object action() {
                    Long merchantId = scheduleVo.getMerchantId();
                    ResultDto<List<EmailConfigVo>> configRes = cityPickPriceApp.findEmailConfigByMerchantId(merchantId, 1L);
                    String merchantName = CommonUtils.queryMerchantName(merchantId);
                    if(configRes.getStatus() == 1){
                        List<EmailConfigVo> configVos = configRes.getResult();
                        if(CollectionUtils.isNotEmpty(configVos)){
                            EmailConfigVo emailConfigVo = configVos.get(0);
                            final String emailAddress = emailConfigVo.getEmailAddress();
                            //  获取当天早9点的时间值
                            String dateStr = ScheduleDateUtils.format(new Date(), "yyyy-MM-dd")+" 09:00:00";
                            String scheduleStartTime = ScheduleDateUtils.format(scheduleVo.getStartTime(),ScheduleDateUtils.DEFAULT_DATE_PATTERN);
                            //  转化mail信息
                            String startTime = ScheduleDateUtils.format(scheduleVo.getStartTime(),ScheduleDateUtils.DEFAULT_DATE_PATTERN);
                            String endTime = ScheduleDateUtils.format(scheduleVo.getEndTime(),ScheduleDateUtils.DEFAULT_DATE_PATTERN);
                            String formatContent = String.format(content, new Object[]{dateStr,merchantName,scheduleStartTime,scheduleVo.getName(),startTime+"~"+endTime});
                            String formatSubject = String.format(subject, new Object[]{merchantName});
                            SendUtil.sendInnerEmail(emailAddress, formatContent, formatSubject);
                        }
                    }
                    return null;
                }
            });
        }
        return new ExecResult<Integer>(result, dataIds);
    }

    @Override
    public int updateData2Processed(final List<Long> ids) {
        return 0;
    }

}