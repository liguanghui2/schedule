<%@ page import="java.net.InetAddress"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
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
		<H1>gps-schedule 切片管理后台</H1>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></p>
		</td>
		<td>待切片的数据表名:&nbsp;&nbsp;<input type="text" style="width: 350px"
			id="input_tableName" />
		<button type="button" id="btn_query_data" style="cursor: pointer;">查询</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_add_data" style="cursor: pointer;"
			onclick="openDialog4Adding()">新增切片</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_edit_data" style="cursor: pointer;"
			onclick="openDialog4Updating()">切片扩容</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_del_ip" style="cursor: pointer;"
			onclick="openDialog4Deleting()">抹除ip</button>&nbsp;&nbsp;
			要删除的切片主键ID:&nbsp;&nbsp;<input type="text" style="width: 350px"
			id="input_sharding_id" />&nbsp;&nbsp;
		<button type="button" id="btn_delete_data" style="cursor: pointer;color:red"
			onclick="deleteShardingPhysics()">删除切片</button>
		</td>
	</tr>
	<tr>
		要删除的周数价格看板数据为:&nbsp;&nbsp;<input type="text" style="width: 350px"
			id="input_week_num" />&nbsp;&nbsp;
		<button type="button"  style="cursor: pointer;color:red"
			onclick="deleteWeekNum()">删除周看板数据</button>
	</tr>
	<tr>
		<td colspan="2">
		<table id="list4"></table>
		<div id="gridPager"></div>
		<div id="consoleDlg">
		<div id="formContainer">
		<form id="consoleForm"><input type="hidden" id="selectId" />
		<table id="formTable" class="formTable">
			<tr>
				<th>主键id：</th>
				<td><input type="text" class="textField" id="param_id"
					name="param_id" style="width: 300px" disabled="true" readOnly=true />
				</td>
			</tr>
			<tr>
				<th>待切片的数据表名：</th>
				<td><textarea id="param_tableName" style="width: 300px"></textarea>
				</td>
			</tr>
			<tr id="tr_shardingCount">
				<th>切片个数：</th>
				<td><input id="param_shardingCount" style="width: 300px"
					maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" /></td>
			</tr>
			<tr id="tr_addShardingCount">
				<th>切片扩容个数：</th>
				<td><input id="param_addShardingCount" style="width: 300px"
					maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')" /></td>
			</tr>
		</table>
		</form>
		</div>
	</div>
	</td>
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
	jQuery(document).ready(
			function() {
				jQuery("#list4").jqGrid(
						{
							url : getHost() + "/admin/sharding",
							datatype : "json", //数据来源
							mtype : "GET",//提交方式
							height : 'auto',//高度，表格高度。可为数值、百分比或'auto'
							autowidth : true,//自动宽
							colNames : [ '主键id', '待切片的数据表名', '切片的ID', '切片是否可用',
									'正在处理此切片的IP地址', '处理切片的开始更新时间' ],
							colModel : [ {
								name : 'id',
								index : 'id',
								width : '10%',
								align : 'center'
							}, {
								name : 'tableName',
								index : 'tableName',
								width : '40%',
								align : 'left'
							}, {
								name : 'shardingIndex',
								index : 'shardingIndex',
								width : '10%',
								align : "left"
							}, {
								name : 'isValid',
								index : 'isValid',
								width : '10%',
								align : "left",
								sortable : false
							}, {
								name : 'ip',
								index : 'ip',
								width : '20%',
								align : "left"
							}, {
								name : 'updateTime',
								index : 'updateTime',
								width : '20%',
								align : "left"
							} ],
							rownumbers : true,//添加左侧行号
							altRows : true,//设置为交替行表格,默认为false
							sortname : 'id',
							sortorder : 'asc',
							viewrecords : true,//是否在浏览导航栏显示记录总数
							loadonce : true,//启用前端分页功能
							rowNum : 15,//每页显示记录数
							rowList : [ 15, 20, 25 ],//用于改变显示行数的下拉列表框的元素数组。
							postData : {
								action : "query"
							},
							jsonReader : {
								root : "gridData",
								page : "page",
								total : "total",
								records : "records",
								repeatitems : false
							},
							pager : jQuery('#gridPager'),
							caption : "切片表-查询结果"//表格标题
						});
				// 配置对话框  
				jQuery("#consoleDlg").dialog({
					autoOpen : false,
					modal : true, // 设置对话框为模态（modal）对话框  
					resizable : true,
					width : 480,
					closeAfterAdd : true,
					closeAfterEdit : true,
					buttons : { // 为对话框添加按钮  
						"取消" : function() {
							jQuery("#consoleDlg").dialog("close")
						},
						"创建" : addSharding,
						"保存" : updateSharding,
						"抹除" : deleteSharding
					}
				});

				jQuery("#btn_query_data").click(
						function() {
							var tableName = jQuery.trim(jQuery(
									"#input_tableName").val());
							jQuery("#list4").jqGrid('setGridParam', {
								datatype : 'json',
								postData : {
									'input' : tableName
								}, //发送数据  
								page : 1
							}).trigger("reloadGrid"); //重新载入  
						});
			});
	var openDialog4Adding = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");

		dialogButtonPanel.find("button:not(:contains('取消'))").hide();
		dialogButtonPanel.find("button:contains('创建')").show();
		consoleDlg.dialog("option", "title", "新增切片").dialog("open");
		jQuery("#formTable").show();
		jQuery("#tr_shardingCount").show();
		jQuery("#tr_addShardingCount").hide();
		jQuery("#param_tableName").attr("disabled", false);
		jQuery("#param_shardingCount").attr("disabled", false);
		jQuery("#param_shardingCount").val("");
	};
	var openDialog4Updating = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");

		dialogButtonPanel.find("button:not(:contains('取消'))").hide();
		dialogButtonPanel.find("button:contains('保存')").show();
		consoleDlg.dialog("option", "title", "修改切片");
		jQuery("#formTable").show();
		jQuery("#tr_shardingCount").show();
		jQuery("#tr_addShardingCount").show();
		consoleDlg.find("#param_tableName").attr("disabled", true);
		consoleDlg.find("#param_shardingCount").attr("disabled", true);

		loadSelectedRowData();
	}
	var openDialog4Deleting = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");

		dialogButtonPanel.find("button:not(:contains('取消'))").hide();
		dialogButtonPanel.find("button:contains('抹除')").show();
		consoleDlg.dialog("option", "title", "确认抹除ip？请确保不影响定时器执行");
		jQuery("#formTable").hide();
		// 打开对话框  
		consoleDlg.dialog("open");
	}
	
	//物理删除切片
	var deleteShardingPhysics = function() {
	   var shardingId = jQuery.trim(jQuery("#input_sharding_id").val());
		var params = {
				"action" : "deleteShardingPhysics",
				"inputId" : shardingId,
			};
		jQuery.ajax({
			url : getHost() + "/admin/sharding",
			data : params,
			dataType : "json",
			cache : false,
			error : function(jqXHR, textStatus, errorThrown) {
				alert("系统ajax交互错误: " + jqXHR.responseText);
			},
			success : function(data, textStatus) {
				if (data == "1") {
					alert("删除成功!");
				} else {
					alert("删除失败!");
				}
			}
		});
	};
	
	//删除周历史价格数据
	var deleteWeekNum = function() {
		   var weekNum = jQuery.trim(jQuery("#input_week_num").val());
			var params = {
					"action" : "deleteWeekNum",
					"inputId" : weekNum,
				};
			jQuery.ajax({
				url : getHost() + "/admin/sharding",
				data : params,
				dataType : "json",
				cache : false,
				error : function(jqXHR, textStatus, errorThrown) {
					alert("系统ajax交互错误: " + jqXHR.responseText);
				},
				success : function(data, textStatus) {
					if (data == "1") {
						alert("删除成功!");
					} else {
						alert("删除失败!");
					}
				}
			});
		};
	
	var loadSelectedRowData = function() {
		var selectedRowId = jQuery("#list4").jqGrid("getGridParam", "selrow");
		if (!selectedRowId) {
			alert("请先选择需要扩容的数据!");
			return false;
		} else {
			var rowData = $("#list4").jqGrid('getRowData', selectedRowId);
			var params = {
				"action" : "query",
				"inputId" : rowData.id
			};
			// 从Server读取对应ID的JSON数据  
			jQuery.ajax({
				url : getHost() + "/admin/sharding",
				data : params,
				dataType : "json",
				cache : false,
				error : function(textStatus, errorThrown) {
					alert("系统ajax交互错误: " + textStatus);
				},
				success : function(data, textStatus) {
					// 如果读取结果成功，则将信息载入到对话框中    
					var rowData = null;
					jQuery.each(data.gridData, function(i, item) {
						rowData = item;
					});
					var consoleDlg = jQuery("#consoleDlg");
					consoleDlg.find("#param_id").val(rowData.id);
					consoleDlg.find("#param_tableName").val(rowData.tableName);
					consoleDlg.find("#param_shardingCount").val(
							data.shardingCount);

					// 打开对话框  
					consoleDlg.dialog("open");
				}
			});
		}
	};
	var addSharding = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var inputTableName = jQuery.trim(consoleDlg.find("#param_tableName")
				.val());
		if (inputTableName == null || inputTableName == "") {
			alert("待切片的数据表名不能为空！");
			return false;
		}
		var inputShardingCount = jQuery.trim(consoleDlg.find(
				"#param_shardingCount").val());
		if (inputShardingCount == null || inputShardingCount == ""
				|| inputShardingCount<=0||inputShardingCount>127) {
			alert("切片的个数不能为空,不能小于1，不能大于127！");
			return false;
		}
		var params = {
			"action" : "add",
			"inputTableName" : inputTableName,
			"inputShardingCount" : inputShardingCount
		};
		jQuery.ajax({
			url : getHost() + "/admin/sharding",
			data : params,
			dataType : "json",
			cache : false,
			error : function(jqXHR, textStatus, errorThrown) {
				alert("系统ajax交互错误: " + jqXHR.responseText);
			},
			success : function(data, textStatus) {
				if (data == "1") {
					jQuery("#input_tableName").val(inputTableName);
					consoleDlg.dialog("close");
					jQuery("#btn_query_data").click();
					alert("新增成功!");
				} else {
					alert("新增失败!");
				}
			}
		});
	};
	var updateSharding = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var inputTableName = jQuery.trim(consoleDlg.find("#param_tableName")
				.val());
		if (inputTableName == null || inputTableName == "") {
			alert("待切片的数据表名不能为空！");
			return false;
		}
		var inputAddShardingCount = jQuery.trim(consoleDlg.find(
				"#param_addShardingCount").val());
		if (inputAddShardingCount == null || inputAddShardingCount == ""
				|| inputAddShardingCount<=0 || inputAddShardingCount>127) {
			alert("切片扩容的个数不能为空,不能小于1，不能大于127！");
			return false;
		}
		var inputShardingCount = jQuery.trim(consoleDlg.find(
				"#param_shardingCount").val());
		 var sum = parseInt(inputAddShardingCount) + parseInt(inputShardingCount);
		if ( sum > 127) {
			alert("切片总个数不能超过127！");
			return false;
		}
		var params = {
			"action" : "update",
			"inputTableName" : inputTableName,
			"inputAddShardingCount" : inputAddShardingCount
		};
		jQuery.ajax({
			url : getHost() + "/admin/sharding",
			data : params,
			dataType : "json",
			cache : false,
			error : function(jqXHR, textStatus, errorThrown) {
				alert("系统ajax交互错误: " + jqXHR.responseText);
			},
			success : function(data, textStatus) {
				if (data == "1") {
					jQuery("#input_tableName").val(inputTableName);
					consoleDlg.dialog("close");
					jQuery("#btn_query_data").click();
					alert("扩容成功!");
					consoleDlg.dialog("close");
				} else {
					alert("扩容失败!");
				}
			}
		});
	};
	var deleteSharding = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var params = {
			"action" : "delete"
		};
		jQuery.ajax({
			url : getHost() + "/admin/sharding",
			data : params,
			dataType : "json",
			cache : false,
			error : function(jqXHR, textStatus, errorThrown) {
				alert("系统ajax交互错误: " + jqXHR.responseText);
			},
			success : function(data, textStatus) {
				if (data == "1") {
					consoleDlg.dialog("close");
					jQuery("#btn_query_data").click();
					alert("抹除成功!");
				} else {
					alert("抹除失败!");
				}
			}
		});
	};
	function getHost() {
		return window.location.protocol + "//" + window.location.host;
	}
</script>
</body>
</html>