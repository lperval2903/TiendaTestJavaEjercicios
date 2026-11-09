<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.iesbelen.model.Producto"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Productos</title>
    <style>
        .clearfix::after {
            content: "";
            display: block;
            clear: both;
        }
    </style>
</head>
<body>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <div class="clearfix">
        <div style="float: left; width: 50%">
            <h1>Productos</h1>
        </div>
        <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
            <div style="position: absolute; left: 5%; top: 39%;">
                <form action="${pageContext.request.contextPath}/tienda_informatica/fabricantes">
                    <input type="submit" value="Fabricantes">
                </form>
            </div>
            <div style="position: absolute; left: 45%; top: 39%;">
                <form action="${pageContext.request.contextPath}/tienda_informatica/producto/crear">
                    <input type="submit" value="Crear Producto">
                </form>
            </div>
        </div>
    </div>
    <div class="clearfix"><hr/></div>
    <form action="${pageContext.request.contextPath}/tienda_informatica/producto" method="get">
        <label for="busqueda">Buscar por nombre:</label>
        <input type="search" name="busqueda" id="busqueda" value="${busqueda}" />
        <input type="submit" value="Buscar" />
    </form>
    <div class="clearfix"><hr/></div>
    <div class="clearfix">
        <div style="float: left;width: 20%">Código</div>
        <div style="float: none;width: auto;overflow: hidden;">Nombre</div>
    </div>
    <div class="clearfix"><hr/></div>
    <%
        List<Producto> productos = (List<Producto>) request.getAttribute("listaProductos");
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
    %>
    <div style="margin-top: 6px;" class="clearfix">
        <div style="float: left;width: 20%"><%= producto.getCodigo() %></div>
        <div style="float: none;width: auto;overflow: hidden;">
            <a href="${pageContext.request.contextPath}/tienda_informatica/producto/<%= producto.getCodigo() %>">
                <%= producto.getNombre() %>
            </a>
        </div>
    </div>
    <%
            }
        } else {
    %>
    No hay registros de producto.
    <%
        }
    %>
</div>
</body>
</html>
