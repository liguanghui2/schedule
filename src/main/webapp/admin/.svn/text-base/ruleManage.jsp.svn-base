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
		<H1>gps-schedule 规则管理后台</H1>
		</td>
	</tr>
	<tr class="tableTitile">
		<td>
		<p><font size="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></p>
		</td>
		<td>查询条件:&nbsp;&nbsp;<input type="text" style="width: 350px"
			id="input_tableName" /> <!-- 暂未实现 -->
		<button type="button" id="btn_query_data" style="cursor: pointer;">查询</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_add_data" style="cursor: pointer;"
			onclick="openDialog4Adding()">新增规则</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_edit_data" style="cursor: pointer;"
			onclick="openDialog4Updating()">修改规则</button>
		&nbsp;&nbsp;
		<button type="button" id="btn_del_data" style="cursor: pointer;"
			onclick="openDialog4Deleting()">删除规则</button>
		</td>
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
				<th align="right">主键id：</th>
				<td><input type="text" class="textField" id="param_id"
					name="param_id" style="width: 300px" disabled="true" readOnly=true />
				</td>
			</tr>
			<tr>
				<th align="right">规则用户名：</th>
				<!-- 后续可以改成下拉框 -->
				<td><input id="param_schema1" style="width: 300px" /></td>
			</tr>
			<tr>
				<th align="right">规则sql：</th>
				<td><textarea id="param_sql1" style="width: 300px"></textarea></td>
			</tr>
			<tr>
				<th align="right">操作类型：</th>
				<td><select id="param_opType">
					<option value="1" selected="selected">></option>
					<option value="2">=</option>
					<option value="3"><</option>
					<option value="4">>=</option>
					<option value="5"><=</option>
					<option value="6">!=</option>
				</select></td>
			</tr>
			<tr>
				<th align="right">阈值：</th>
				<td><input id="param_threshold" style="width: 300px"
					onkeyup="value=value.replace(/[^\d]/g,'')" /></td>
			</tr>
			<tr>
				<th align="right">规则类型：</th>
				<td><input id="param_ruleType" style="width: 300px" /></td>
			</tr>
			<tr>
				<th align="right">报警用户名：</th>
				<td><input id="param_schema2" style="width: 300px" /></td>
			</tr>
			<tr>
				<th align="right">报警sql：</th>
				<td><textarea id="param_sql2" style="width: 300px"></textarea></td>
			</tr>
			<tr>
				<th align="right">报警内容：</th>
				<td><textarea id="param_warningContent" style="width: 300px"></textarea></td>
			</tr>
			<tr>
				<th align="right">报警收件人邮箱：</th>
				<td><textarea id="param_email" style="width: 300px"></textarea></td>
			</tr>
			<tr>
				<th align="right">是否生效：</th>
				<td><select id="param_isValid">
					<option selected="selected">true</option>
					<option>false</option>
				</select></td>
			</tr>
			<tr>
				<th align="right">备注：</th>
				<td><textarea id="param_remark" style="width: 300px"></textarea></td>
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
							url : getHost() + "/admin/ruleManage",
							datatype : "json", //数据来源
							mtype : "GET",//提交方式
							height : 'auto',//高度，表格高度。可为数值、百分比或'auto'
							autowidth : true,//自动宽
							colNames : [ '主键id', '规则用户名', '规则sql', '操作类型', '阈值', '规则类型', '报警用户名', 
							             '报警sql', '报警内容', '报警收件人邮箱', '是否生效', '备注' , '创建时间', '更新时间' ],
							colModel : [ {
								name : 'id',
								index : 'id',
								width : '10%',
								align : 'center'
							}, {
								name : 'schema1',
								index : 'schema1',
								width : '15%',
								align : 'left'
							}, {
								name : 'sql1',
								index : 'sql1',
								width : '40%',
								align : "left"
							}, {
								name : 'opType',
								index : 'opType',
								width : '15%',
								edittype : 'select',
								formatter : 'select',
								editoptions : {value:'1:>;2:=;3:<;4:>=;5:<=;6:!=;'},
								align : "left"
							}, {
								name : 'threshold',
								index : 'threshold',
								width : '8%',
								align : "left"
							}, {
								name : 'ruleType',
								index : 'ruleType',
								width : '15%',
								align : "left"
							}, {
								name : 'schema2',
								index : 'schema2',
								width : '15%',
								align : "left",
								sortable : false
							}, {
								name : 'sql2',
								index : 'sql2',
								width : '40%',
								align : "left",
								sortable : false
							}, {
								name : 'warningContent',
								index : 'warningContent',
								width : '40%',
								align : "left",
								sortable : false
							}, {
								name : 'email',
								index : 'email',
								width : '30%',
								align : "left",
								sortable : false
							}, {
								name : 'isValid',
								index : 'isValid',
								width : '15%',
								align : "left",
								sortable : false
							}, {
								name : 'remark',
								index : 'remark',
								width : '20%',
								align : "left",
								sortable : false
							}, {
								name : 'createTime',
								index : 'createTime',
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
							caption : "规则表-查询结果"//表格标题
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
						"创建" : addRule,
						"保存" : updateRule,
						"删除" : deleteRule
					}
				});

				jQuery("#btn_query_data").click(
						function() {
							jQuery("#list4").jqGrid('setGridParam', {
								datatype : 'json',
								postData : {}, //发送数据  
								page : 1
							}).trigger("reloadGrid"); //重新载入  
						});
			});
	var openDialog4Adding = function() {  
	    var consoleDlg = jQuery("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	    
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('创建')").show();  
	    consoleDlg.dialog("option", "title", "新增规则").dialog("open");  
	};  
	var openDialog4Updating = function() {  
	    var consoleDlg = jQuery("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	      
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('保存')").show();  
	    consoleDlg.dialog("option", "title", "修改规则");  
	      
	    loadSelectedRowData();  
	}  
	var openDialog4Deleting = function() {  
	    var consoleDlg = jQuery("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	      
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('删除')").show();  
	    consoleDlg.dialog("option", "title", "删除规则");  
	      
	    loadSelectedRowData(); 
	}  
	var loadSelectedRowData = function() {  
	    var selectedRowId = jQuery("#list4").jqGrid("getGridParam", "selrow"); 
	    if (!selectedRowId) {  
	        alert("请先选择需要编辑的行!");  
	        return false;  
	    } else {  
	    	var rowData=$("#list4").jqGrid('getRowData',selectedRowId);
	        var params = {  
	        	"action" : "query",
	            "id" : rowData.id  
	        };  
	        // 从Server读取对应ID的JSON数据  
	        jQuery.ajax( {  
	            url : getHost() + "/admin/ruleManage",  
	            data : params,  
	            dataType : "json",  
	            cache : false,  
	            error : function(textStatus, errorThrown) {  
	                alert("系统ajax交互错误: " + textStatus);  
	            },  
	            success : function(data, textStatus) {  
	                // 如果读取结果成功，则将信息载入到对话框中    
	                var rowData=null;
	                jQuery.each(data.gridData,function(i,item){
	                	rowData=item;
	                });
	                var consoleDlg = jQuery("#consoleDlg");  
	                consoleDlg.find("#param_id").val(rowData.id);  
	                consoleDlg.find("#param_schema1").val(rowData.schema1);  
	                consoleDlg.find("#param_sql1").val(rowData.sql1); 
	                consoleDlg.find("#param_threshold").val(rowData.threshold); 
	                consoleDlg.find("#param_ruleType").val(rowData.ruleType); 
	                consoleDlg.find("#param_schema2").val(rowData.schema2); 
	                consoleDlg.find("#param_sql2").val(rowData.sql2); 
	                consoleDlg.find("#param_email").val(rowData.email); 
	                consoleDlg.find("#param_warningContent").val(rowData.warningContent); 
	                consoleDlg.find("#param_isValid").get(0).value=rowData.isValid;  
	                consoleDlg.find("#param_remark").val(rowData.remark); 
	                consoleDlg.find("#param_opType").get(0).value=rowData.opType; 
	                // 根据新载入的数据将表格中的对应数据行一并更新一下  
	                var dataRow = {  
	                        id : rowData.id,  
	                        schema1 : rowData.schema1,  
	                        sql1 : rowData.sql1,  
	                        threshold : rowData.threshold,  
	                        schema2 : rowData.schema2,  
	                        sql2 : rowData.sql2,  
	                        email : rowData.email,  
	                        warningContent : rowData.warningContent,  
	                        isValid : rowData.isValid,  
	                        remark : rowData.remark,  
	                        opType : rowData.opType
	                    };  
	                jQuery("#list4").jqGrid("setRowData", data.gridData.id, dataRow);  
	                      
	                // 打开对话框  
	                consoleDlg.dialog("open");  
	            }  
	        });  
	    }  
	}; 
	var addRule = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var schema1 = jQuery.trim(consoleDlg.find("#param_schema1")
				.val());
		if (schema1 == null || schema1 == "") {
			alert("规则用户名不能为空！");
			return false;
		}
		var sql1 = jQuery.trim(consoleDlg.find("#param_sql1")
				.val());
		if (sql1 == null || sql1 == "") {
			alert("规则sql不能为空！");
			return false;
		}
		var threshold = jQuery.trim(consoleDlg.find("#param_threshold")
				.val());
		if (threshold == null || threshold == "") {
			alert("阈值不能为空！");
			return false;
		}
		var ruleType = jQuery.trim(consoleDlg.find("#param_ruleType")
				.val());
		var schema2 = jQuery.trim(consoleDlg.find("#param_schema2")
				.val());
		if (schema2 == null || schema2 == "") {
			alert("报警用户名不能为空！");
			return false;
		}
		var sql2 = jQuery.trim(consoleDlg.find("#param_sql2")
				.val());
		if (sql2 == null || sql2 == "") {
			alert("报警sql不能为空！");
			return false;
		}
		var warningContent = jQuery.trim(consoleDlg.find("#param_warningContent")
				.val());
		var email = jQuery.trim(consoleDlg.find("#param_email")
				.val());
		var isValid = jQuery.trim(consoleDlg.find("#param_isValid")
				.val());
		var remark = jQuery.trim(consoleDlg.find("#param_remark")
				.val());
		var opType = jQuery.trim(consoleDlg.find("#param_opType")
				.val());
		var params = {
			"action" : "add",
			"schema1" : schema1,
			"sql1" : sql1,
			"threshold" : threshold,
			"ruleType" : ruleType,
			"schema2" : schema2,
			"sql2" : sql2,
			"warningContent" : warningContent,
			"email" : email,
			"isValid" : isValid,
			"remark" : remark,
			"opType" : opType
		};
		jQuery.ajax({
			url : getHost() + "/admin/ruleManage",
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
					alert("新增成功!");
				} else {
					alert("新增失败!");
				}
			}
		});
	};
	var updateRule = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var id = jQuery.trim(consoleDlg.find("#param_id")
				.val());
		var schema1 = jQuery.trim(consoleDlg.find("#param_schema1")
				.val());
		if (schema1 == null || schema1 == "") {
			alert("规则用户名不能为空！");
			return false;
		}
		var sql1 = jQuery.trim(consoleDlg.find("#param_sql1")
				.val());
		if (sql1 == null || sql1 == "") {
			alert("规则sql不能为空！");
			return false;
		}
		var threshold = jQuery.trim(consoleDlg.find("#param_threshold")
				.val());
		if (threshold == null || threshold == "") {
			alert("阈值不能为空！");
			return false;
		}
		var schema2 = jQuery.trim(consoleDlg.find("#param_schema2")
				.val());
		if (schema2 == null || schema2 == "") {
			alert("报警用户名不能为空！");
			return false;
		}
		var sql2 = jQuery.trim(consoleDlg.find("#param_sql2")
				.val());
		if (sql2 == null || sql2 == "") {
			alert("报警sql不能为空！");
			return false;
		}
		var ruleType = jQuery.trim(consoleDlg.find("#param_ruleType")
				.val());
		var warningContent = jQuery.trim(consoleDlg.find("#param_warningContent")
				.val());
		var email = jQuery.trim(consoleDlg.find("#param_email")
				.val());
		var isValid = jQuery.trim(consoleDlg.find("#param_isValid")
				.val());
		var remark = jQuery.trim(consoleDlg.find("#param_remark")
				.val());
		var opType = jQuery.trim(consoleDlg.find("#param_opType")
				.val());
		var params = {
			"action" : "update",
			"id" : id,
			"schema1" : schema1,
			"sql1" : sql1,
			"ruleType" : ruleType,
			"threshold" : threshold,
			"schema2" : schema2,
			"sql2" : sql2,
			"warningContent" : warningContent,
			"email" : email,
			"isValid" : isValid,
			"remark" : remark,
			"opType" : opType
		};
		jQuery.ajax({
			url : getHost() + "/admin/ruleManage",
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
					alert("更新成功!");
					consoleDlg.dialog("close");
				} else {
					alert("更新失败!");
				}
			}
		});
	};
	var deleteRule = function() {
		var consoleDlg = jQuery("#consoleDlg");
		var id = jQuery.trim(consoleDlg.find("#param_id")
				.val());
		if(id==null){
			alert("请选择要删除的数据行！");
			return false;
		}
		var params = {
			"action" : "delete",
			"id" : id
		};
		jQuery.ajax({
			url : getHost() + "/admin/ruleManage",
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
					alert("删除成功!");
				} else {
					alert("删除失败!");
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