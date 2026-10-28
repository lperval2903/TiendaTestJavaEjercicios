<%--
  Created by IntelliJ IDEA.
  User: liber
  Date: 26/10/2026
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.iesbelen.model.Producto"%>
<%@ page import="java.util.Optional"%>
<html>
<head>
    <title>Crear Producto</title>
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
    <form action="${pageContext.request.contextPath}/tienda_informatica/producto/crear/" method="post">
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Crear Producto</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Crear"/>
                </div>

            </div>
        </div>

        <div class="clearfix">
            <hr/>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Nombre
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="nombre" />
            </div>
        </div>

    </form>
</div>
</body>
</html>
