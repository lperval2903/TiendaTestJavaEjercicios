<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.iesbelen.model.Producto"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle del producto</title>
</head>
<body>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    if (producto != null) {
%>
    <h1>Detalle del producto</h1>
    <p>Código: <%= producto.getCodigo() %></p>
    <p>Nombre: <%= producto.getNombre() %></p>
<%
    } else {
%>
    <p>Producto no encontrado.</p>
<%
    }
%>
<a href="${pageContext.request.contextPath}/tienda_informatica/producto">Volver a productos</a>
</body>
</html>
