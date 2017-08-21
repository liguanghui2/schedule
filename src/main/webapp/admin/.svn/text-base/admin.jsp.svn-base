<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>gps-schedule</title>
<link rel="shortcut icon" href="/favicon.ico" />
<link type="text/css" rel="stylesheet"
	href="jqGrid/css/cupertino/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="jqGrid/css/ui.jqgrid.css">
</head>
<head>
<style>
.tableTitile td {
	border: 1px solid;
	padding: 6px;
}

.msgDiv {
	height: 100px;
	width: 80%;
	border: 1px solid;
}

#title td {
	font-size: 16px;
	font-weight: bold;
}
</style>
<script type="text/javascript" src="jQuery.md5.js"></script>
<script src="jqGrid/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="jqGrid/js/jquery-ui.js" type="text/javascript"></script>
<script src="jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>

</head>
<body style="">
<table id="table" style="width: 100%;" cellspacing="0" cellpadding="2">
	<tr align="center" class="tableTitile">
		<td colspan="2" height="50px">
		<H1>gps-schedule 管理后台</H1>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">统计表管理</font></p>
		</td>
		<td>
		<button type="button" id="btn_delete_manage" style="cursor: pointer;">删除统计表无用数据</button>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">切片管理</font></p>
		</td>
		<td>
		<button type="button" id="btn_sharding_manage"
			style="cursor: pointer;">点击进入切片管理页面</button>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">规则管理</font></p>
		</td>
		<td>
		<button type="button" id="btn_rule_manage"
			style="cursor: pointer;">点击进入规则管理页面</button>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">扫描表管理</font></p>
		</td>
		<td>
		<table border=0 cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" style="border: 0">价格促销加入扫描表&nbsp;ruleId:</td>
				<td style="border: 0"><input type="text" style="width: 350px"
					id="input_ruleId" />&nbsp;&nbsp;
				<button type="button" id="btn_add_ruleId" style="cursor: pointer;">执行</button>
				&nbsp;&nbsp;
				<button type="button" id="btn_init_scanner" style="cursor: pointer;">初始化扫描表</button>
				</td>
			</tr>
			<tr></tr>
		</table>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">补偿同步京东图书价格</font></p>
		</td>
		<td>
		<table border=0 cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" style="border: 0">补偿同步京东图书价格&nbsp;pmId(以,隔开):</td>
				<td style="border: 0"><textarea style="width: 350px"
					id="input_pmId" ></textarea>&nbsp;&nbsp;
				<button type="button" id="btn_compensate_price" style="cursor: pointer;">执行</button>
				</td>
			</tr>
			<tr></tr>
		</table>
		</td>
	</tr>
	<tr class="tableTitile">
		<td></td>
		<td><textarea cols="10" style="width: 100%; height: 300px;"
			id="result_content">&nbsp;&nbsp;
            </textarea></td>
	</tr>
</table>
<script type="text/javascript">
	jQuery.extend({
		toJSON : function(object) {
			var type = typeof object;
			if ('object' == type) {
				if (object != null && Array == object.constructor)
					type = 'array';
				else if (object != null && RegExp == object.constructor)
					type = 'regexp';
				else
					type = 'object';
			}
			switch (type) {
			case 'undefined':
			case 'unknown':
				return;
				break;
			case 'function':
			case 'boolean':
			case 'regexp':
				return object.toString();
				break;
			case 'number':
				return isFinite(object) ? object.toString() : 'null';
				break;
			case 'string':
				return '"'
						+ object.replace(/(\\|\")/g, "\\$1").replace(
								/\n|\r|\t/g,
								function() {
									var a = arguments[0];
									return (a == '\n') ? '\\n'
											: (a == '\r') ? '\\r'
													: (a == '\t') ? '\\t' : ""
								}) + '"';
				break;
			case 'object':
				if (object === null)
					return 'null';
				var results = [];
				for ( var property in object) {
					var value = jQuery.toJSON(object[property]);
					if (value !== undefined)
						results.push(jQuery.toJSON(property) + ':' + value);
				}
				return '{' + results.join(',') + '}';
				break;
			case 'array':
				var results = [];
				for ( var i = 0; i < object.length; i++) {
					var value = jQuery.toJSON(object[i]);
					if (value !== undefined)
						results.push(value);
				}
				return '[' + results.join(',') + ']';
				break;
			}
		},
		evalJSON : function(strJson) {
			return eval("(" + strJson + ")");
		}
	});
	jQuery(document)
			.ready(
					function() {
						jQuery("#btn_sharding_manage").click(function() {
							window.open(getHost() + "/admin/sharding.jsp");
						});

						jQuery("#btn_add_ruleId")
								.click(
										function() {
											var ruleId = jQuery.trim(jQuery(
													"#input_ruleId").val());
											if (ruleId == null || ruleId == "") {
												alert("请输入ruleId！");
												return false;
											}
											var url = "/admin/scanner?action=addRuleId&ruleId="
													+ ruleId;
											jQuery.getJSON(url, function(rs) {
												var v = jQuery.toJSON(rs);
												jQuery("#result_content")
														.val(v);
											});
										});

						jQuery("#btn_init_scanner").click(function() {
							var url = "/admin/scanner?action=initScanner";
							jQuery.getJSON(url, function(rs) {
								var v = jQuery.toJSON(rs);
								jQuery("#result_content").val(v);
							});
						});
						
						// 统计表管理
						jQuery("#btn_delete_manage").click(function() {
							var url = "/admin/manage?action=deleteDateSectionPrice";
							jQuery.getJSON(url, function(rs) {
								var v = jQuery.toJSON(rs);
								jQuery("#result_content").val(v);
							});
						});

						jQuery("#btn_add_promotionId")
								.click(
										function() {
											alert("该功能暂未开放");
											return false;
											var promotionId = jQuery
													.trim(jQuery(
															"#input_promotionId")
															.val());
											if (promotionId == null
													|| promotionId == "") {
												alert("请输入promotionId！");
												return false;
											}
											var url = "/admin/scanner?action=addpromotionId&promotionId="
													+ promotionId;
											jQuery.getJSON(url, function(rs) {
												var v = jQuery.toJSON(rs);
												jQuery("#result_content")
														.val(v);
											});
										});
						
						//补偿同步京东图书价格
						jQuery("#btn_compensate_price")
						.click(
								function() {
									var pmIds = jQuery.trim(jQuery(
											"#input_pmId").val());
									if (pmIds == null || pmIds == "") {
										alert("请输入pmId！");
										return false;
									}
									var url = "/admin/compensate?action=compensateJDBookPrice&pmIds="
											+ pmIds;
									jQuery.getJSON(url, function(rs) {
										var v = jQuery.toJSON(rs);
										jQuery("#result_content")
												.val(v);
									});
								});
						jQuery("#btn_rule_manage").click(function() {
							window.open(getHost() + "/admin/ruleManage.jsp");
						});

					});
	function getHost() {
		return window.location.protocol + "//" + window.location.host;
	}
</script>
</body>
</html>