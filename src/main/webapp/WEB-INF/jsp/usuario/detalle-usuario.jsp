<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.iesbelen.model.Usuario" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Detalle de usuario</title></head>
<body>
<% Usuario usuario = (Usuario) request.getAttribute("usuario"); %>
<h1>Detalle de usuario</h1>
<p>ID: <%= usuario.getIdUsuario() %></p>
<p>Usuario: <%= usuario.getUsuario() %></p>
<p>Rol: <%= usuario.getRol() %></p>
<a href="${pageContext.request.contextPath}/tienda_informatica/usuarios">Volver</a>
</body>
</html>
