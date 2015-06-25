<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Bidder Page</title>
		<style type="text/css">
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
	</style>
	
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="themes/demo.css">
	
	<script type="text/javascript">
		var url;
		function newBid() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('setTitle', 'Place Bid');
				$('#fm').form('clear');
				$('#fm').form('load', row);
				url = 'placeBid.action?id=' + row.id;	
			}
		}
		function placeBid() {
			$('#fm').form(
				'submit', {
					url: url,
					onSubmit: function() {return $(this).form('validate');},
					success: function(result) {
						var result = eval('('+result+')');
						if (result.success) {
							$('#dlg').dialog('close');
							$('#dg').datagrid('reload');
						} else {
							$.messager.show({
								title: 'Error',
								msg: result.msg
							});
						}
					}
				}
			);
		}
		function rowformatter(value,row,index) {
			var et = row.endTime.replace(/-/ig,'/');
			var etd = new Date(et);
			var nt = new Date();
			if (nt > etd) {
				return "Auction closed.";
			} else {
				return "<a href='#' onclick='newBid()'>Place Bid</a>";
			}
		}
		
		function auctionClosedAlert() {
			$.ajax({
				type: 'post',
				url: 'closeAuctionAlert',
				success : function(result) {
					if (result.success) {
						alert(result.msg);
					}
				}
			});	
		}
		
		function refreshPage(){
			   window.location.reload();
			}
		
		setInterval('actionClosedAlert()',60000);
		setInterval('refreshPage()',60000);
	</script>
</head>
<body>
	<h2>Book Auction</h2>
	<div class="switch-page">
		<a href='Administrator.jsp'>I am an administrator.</a>
	</div>
	<table 
		id="dg" title="Books on Auction" class="easyui-datagrid" style="width:auto; height:auto"
		url="getBook" rownumbers="true" fitColumns="true" singleSelect="true" autoRowHeight="true" nowrap="false">
		
		<thead>
			<tr>
				<th field="id" width="50">id</th>
				<th field="name" width="200">Book Name</th>
				<th field="descr" width="600">Description</th>
				<th field="startingPrice" width="250">Starting Price</th>
				<th field="startTime" width="300">Start Time</th>
				<th field="endTime" width="300">End Time</th>
				<th field="minIncre" width="250">Minimal Increment</th>
				<th field="highestBid" width="250">Highest Bid</th>
				<th field="button" width="200" formatter="rowformatter">Place a Bid</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Place a Bid</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>Book id:</label>
				<input name="id" class="easyui-validatebox" readonly="true"> 
			</div>
			<div class="fitem">
				<label>Highest Bid:</label>
				<input name="highestBid" class="easyui-validatebox" readonly="true"> 
			</div>
			<div class="fitem">
				<label>Name:</label>
				<input name="bidder" class="easyui-validatebox" required="true"> 
			</div>
			<div class="fitem">
				<label>Bid:</label>
				<input name="bid" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="placeBid()">Place Bid</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>