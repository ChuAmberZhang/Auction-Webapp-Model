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
		function destroyBook() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm','Are you sure you want to remove this book from the auction?',function(r) {
					if (r) {
						$.post('removeBook', {id:row.id}, function (resutl) {
							if (result.success) {
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({title: 'Error', msg: result.msg});	
							}
						}, 'json');
					}
				});
			}
		}
	</script>
</head>
<body>
	<h2>Book Action Administrator Page</h2>
	<table
		id="dg" title="Books on Auction" class="easyui-datagrid" style="width:550px; height:250px"
		url="getBook" rownumbers="true" fitColumns="true" singleSelect="true">
		
		<thead>
			<tr>
				<th field="id" width="100">id</th>
				<th field="name" width="100">Book Name</th>
				<th field="desc" width="100">Description</th>
				<th field="startingPrice" width="100">Starting Price</th>
				<th field="startTime" width="100">Start Time</th>
				<th field="endTime" width="100">End Time</th>
				<th field="minIncre" width="100">Minimal Increment</th>
				<th field="highestBid" width="100">Highest Bid</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="openHistory()">Check Bid History</a>
	</div>
	
	<div id="dlg-b" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Add a new Book</div>
		<form id="fm-b" method="post" novalidate>
			<div class="fitem">
				<label>Book Name</label>
				<input name="name" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Book Description</label>
				<input name="desc" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Starting Price</label>
				<input name="startingPrice" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Start Time</label>
				<input name="startTime" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>End Time</label>
				<input name="endTime" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Minimal Increase per Bid</label>
				<input name="minIncre" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	
	<div id="dlg-h" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<table id="history" title="Bid Histroy" class="easyui-datagrid" style="width:550px;height:250px"
			url="getBidHistoryById" rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="id" width="100">Book id</th>
					<th field="bidder" width="100">Bidder</th>
					<th field="bid" width="100">Bid</th>
					<th field="time" width="100">Time</th>
				</tr>
			</thead>
		</table>
	</div>
		
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>