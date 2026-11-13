<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.iesbelen.model.Usuario" %>
<html>
<head>
    <title>Usuarios</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragmentos/header.jsp" />
<h1>Usuarios</h1>
<a href="${pageContext.request.contextPath}/tienda_informatica/usuarios/crear">Crear usuario</a>
<table border="1">
    <tr><th>ID</th><th>Usuario</th><th>Rol</th><th>Acciones</th></tr>
<% for (Usuario usuario : (List<Usuario>) request.getAttribute("usuarios")) { %>
    <tr>
        <td><%= usuario.getIdUsuario() %></td>
        <td><%= usuario.getUsuario() %></td>
        <td><%= usuario.getRol() %></td>
        <td>
            <a href="${pageContext.request.contextPath}/tienda_informatica/usuarios/<%= usuario.getIdUsuario() %>">Ver</a>
            <a href="${pageContext.request.contextPath}/tienda_informatica/usuarios/editar/<%= usuario.getIdUsuario() %>">Editar</a>
            <form method="post" action="${pageContext.request.contextPath}/tienda_informatica/usuarios/borrar/<%= usuario.getIdUsuario() %>" style="display:inline">
                <button type="submit">Borrar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<jsp:include page="/WEB-INF/jsp/fragmentos/footer.jsp" />
</body>
</html>
