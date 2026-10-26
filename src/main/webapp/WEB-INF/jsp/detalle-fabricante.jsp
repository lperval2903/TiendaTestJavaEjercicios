<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@page import="org.iesbelen.model.FabricanteDTO"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Detalle Fabricante</title>
	<style>
		.clearfix::after {
			content: "";
			display: block;
			clear: both;
		}
	</style>
</head>
<body>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<div class="clearfix">
		<div style="float: left; width: 50%">
			<h1>Detalle Fabricante</h1>
		</div>
		<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
			<div style="position: absolute; left: 39%; top : 39%;">
				<form action="${pageContext.request.contextPath}/tienda_informatica/fabricantes" >
					<input type="submit" value="Volver" />
				</form>
			</div>
		</div>
	</div>

	<div class="clearfix">
		<hr/>
	</div>

	<%
		FabricanteDTO fab = (FabricanteDTO) request.getAttribute("fabricante");
		if (fab != null) {
	%>

	<div style="margin-top: 6px;" class="clearfix">
		<div style="float: left;width: 50%">
			<label>Código</label>
		</div>
		<div style="float: none;width: auto;overflow: hidden;">
			<input value="<%= fab.getCodigo() %>" readonly="readonly"/>
		</div>
	</div>

	<div style="margin-top: 6px;" class="clearfix">
		<div style="float: left;width: 50%">
			<label>Nombre</label>
		</div>
		<div style="float: none;width: auto;overflow: hidden;">
			<input value="<%= fab.getNombre() %>" readonly="readonly"/>
		</div>
	</div>

	<div style="margin-top: 6px;" class="clearfix">
		<div style="float: left;width: 50%">
			<label># Productos</label>
		</div>
		<div style="float: none;width: auto;overflow: hidden;">
			<input value="<%= fab.getCountProductos() %>" readonly="readonly"/>
		</div>
	</div>

	<% } else { %>
	<p>Fabricante no encontrado.</p>
	<% } %>

</div>

</body>
</html>