<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>landingPage促销比对异常数据</title>
</head>
<body>
<h3>${content}</h3>
<table cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:.2em 0;">
	<tr style="background:#57A7BF;color:#fff;">
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">序号</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">promotionId</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">表promotionV2</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">表gps_promotion</th>
	</tr>
	<#list resultList as item> 
		<#if item_index %2 = 1>  
			<tr style="background:#e5f1f4;">
		<#else>   
			<tr style="background:#f8fbfc;">
		</#if>  
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item_index+1}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item?c}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;"><a style="color:#057fac;" href="https://haikui.yihaodian.cn/haikui/select.jsp?jndi=ora-prod&sql=SELECT P.ID as promotionId, PLP.ACTIVITY_PRICE as activityPrice, PLP.ACTIVITY_POINT as activityPoint, P.START_DATE as startTime, P.END_DATE as endTime, P.IS_PERIOD as isPeriod, PLP.TOTAL_QUANTITY_TYPE as quantityType, PLP.TOTAL_QUANTITY as stockNum, PLP.SOLD_NUM as soldNum, PLP.PRODUCT_ID as productId, PLP.MERCHANT_ID as merchantId, PLP.PM_INFO_ID as pmId FROM pss_data.PROMOTIONV2 P JOIN pss_data.PROMOTIONV2_LANDING_PRODUCT PLP ON P.ID = PLP.PROMOTION_ID WHERE P.IS_DELETE = 0 AND P.TYPE = 4 AND PLP.IS_EFFECTIVE = 1 AND P.START_DATE < to_date('${endDate}','yyyy-mm-dd hh24:mi:ss')  AND P.END_DATE >= to_date('${startDate}','yyyy-mm-dd hh24:mi:ss') AND PLP.PROMOTION_ID = ${item?c} &sql1=SELECT P.ID as promotionId, PLP.ACTIVITY_PRICE as activityPrice, PLP.ACTIVITY_POINT as activityPoint, P.START_DATE as startTime, P.END_DATE as endTime, P.IS_PERIOD as isPeriod, PLP.TOTAL_QUANTITY_TYPE as quantityType, PLP.TOTAL_QUANTITY as stockNum, PLP.SOLD_NUM as soldNum, PLP.PRODUCT_ID as productId, PLP.MERCHANT_ID as merchantId, PLP.PM_INFO_ID as pmId FROM pss_data.PROMOTIONV2 P JOIN pss_data.PROMOTIONV2_LANDING_PRODUCT PLP ON P.ID = PLP.PROMOTION_ID WHERE P.IS_DELETE = 0 AND P.TYPE = 4 AND PLP.IS_EFFECTIVE = 1 AND P.START_DATE < to_date('${endDate}','yyyy-mm-dd hh24:mi:ss') AND P.END_DATE >= to_date('${startDate}','yyyy-mm-dd hh24:mi:ss') AND PLP.PROMOTION_ID = ${item?c}">查看</a></td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;"><a style="color:#057fac;" href="https://haikui.yihaodian.cn/haikui/select.jsp?jndi=mysql-gps&sql=select * from gps.gps_promotion t where t.status = 1 and t.start_time < '${endDate}' and t.end_time >='${startDate}' and t.promotion_id = ${item?c} &sql1=select * from gps.gps_promotion t where t.status = 1 and t.start_time < '${endDate}' and t.end_time >='${startDate}' and t.promotion_id = ${item?c}">查看</a></td>
		</tr>
	</#list> 
</table>
</body>
</html>