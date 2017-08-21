<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>丢失的历史价格数据</title>
</head>
<body>
<h3>丢失的历史价格数据如下:</h3>
<table cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:.2em 0;">
	<tr style="background:#57A7BF;color:#fff;">
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">商品ID</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">渠道ID</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">产品ID</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">价格</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">价格ID</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">当前活动ID</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">促销类型</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">促销规则类型</th>
		<th style="text-align:left;padding:.2em;border:1px solid #fff;">促销规则名称</th>
	</tr>
	<#list resultList as item> 
		<#if item_index %2 = 1>  
			<tr style="background:#e5f1f4;">
		<#else>
			<tr style="background:#f8fbfc;">
		</#if> 
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.pmInfoId?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.channelId?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.productId?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.price?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.priceId?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.promRuleId?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.promoteType?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.ruleType?default('null')}</td>
			<td style="text-align:left;padding:.2em;border:1px solid #fff;">${item.promName?default('null')}</td>
		</tr>
	</#list> 
</table>
</body>
</html>