<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Administrator Page</title>
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
		function newBook() {
			$('#dlg-b').dialog('open').dialog('setTitle','New Book');
			$('#fm-b').form('clear');
			url = "saveBook";
		}
		function openHistory() {
			$('#dlg-h').dialog('open').dialog('setTitle', "Bid History");
		}
		function getHistory() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				alert("row id:" + row.id);
				$('#history').datagrid({
					url:'getBidHistoryById?id=' + row.id,
					columns:[[
						{field:'id', title:'Book id', width:100},
						{field:'bid', title:'Bid', width:100},
						{field:'bidder', title:'Bidder', width:100},
						{field:'time', title:'Time', width:100}]]
				});
			}	
			$('#dlg-h').dialog('open').dialog('setTitle', "Bid History");
		}
		function editBook() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg-b').dialog('open').dialog('setTitle','Edit Book');
				$('#fm-b').form('load', row);
				url = 'updateBook.action?id=' + row.id;
			}
		}
		function saveBook() {
			$('#fm-b').form(
				'submit', {
					url: url,
					onSubmit: function() {return $(this).form('validate');},
					success: function(result) {
						
						var result = eval('('+result+')');
						if (result.success) {
							
							$('#dlg-b').dialog('close');
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
		function destroyBook() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm','Are you sure you want to remove this book from the auction?',function(r) {
					if (r) {
						alert("confirmed removal");
						$.post('removeBook', {id:row.id}, function (result) {
							if (result.success) {
								alert("success");
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({title: 'Error', msg: result.msg});	
							}
						}, 'json');
					}
				});
			}
		}
/* 		function timeFormatter() {
            var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();  
            var str = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);  
            return str;
		}
 		function timeParser() {
            if (!s) return new Date();  
            var y = s.substring(0,4);  
            var m =s.substring(5,7);  
            var d = s.substring(8,10);  
            var h = s.substring(11,13);  
            var min = s.substring(14,16);  
            var sec = s.substring(17,19);  
            if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){  
                return new Date(y,m-1,d,h,min,sec);  
            } else {  
                return new Date();  
            }  
		}  */
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
		
		function refreshPage() {
			window.location.reload();
		}
		
		setInterval('actionClosedAlert()',60000);
		setInterval('refreshPage()',60000);
	</script>
</head>
<body>
	<h2>Book Action Administrator Page</h2>
	<div class="switch-page">
		<a href='index.jsp'>Back to bidder page.</a>
	</div>
	<table
		id="dg" title="Books on Auction" class="easyui-datagrid" style="width:auto; height:auto"
		url="getBook" rownumbers="true" fitColumns="true" singleSelect="true" autoRowHeight="true" nowrap="false">
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBook()">New Book</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">Edit Book</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyBook()">Remove Book</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="getHistory()">Check Bid History</a>
		</div>
		
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
			</tr>
		</thead>
	</table>
	
	<div id="dlg-b" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" >
		<div class="ftitle">Add a new Book</div>
		<form id="fm-b" method="post" novalidate>
			<div class="fitem">
				<label>Book Name</label>
				<input name="name" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Book Description</label>
				<input name="descr" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Starting Price</label>
				<input name="startingPrice" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Start Time</label>
				<input name="startTime" class="easyui-datetimebox" style="width:200px;">
			</div>
			<div class="fitem">
				<label>End Time</label>
				<input name="endTime" class="easyui-datetimebox" style="width:200px;">
			</div>
			<div class="fitem">
				<label>Minimal Increase per Bid</label>
				<input name="minIncre" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBook()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg-b').dialog('close')">Cancel</a>
	</div>
	
	<div id="dlg-h" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true">
<!-- 		<table id="history" title="Bid Histroy" class="easyui-datagrid" style="width:550px;height:250px"
			url="getHistoryUrl()" rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="id" width="100">Book id</th>
					<th field="bidder" width="100">Bidder</th>
					<th field="bid" width="100">Bid</th>
					<th field="time" width="100">Time</th>
				</tr>
			</thead>
		</table> -->
		<table id="history" title="Bid Histroy" style="width:550px;height:250px" rownumbers="true" fitColumns="true" singleSelect="true"></table>
	</div>
		

</body>
</html>