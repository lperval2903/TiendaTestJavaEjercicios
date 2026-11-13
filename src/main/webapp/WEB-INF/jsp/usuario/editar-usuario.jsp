<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.iesbelen.model.Usuario" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Editar usuario</title></head>
<body>
<% Usuario usuario = (Usuario) request.getAttribute("usuario"); %>
<h1>Editar usuario</h1>
<form method="post" action="${pageContext.request.contextPath}/tienda_informatica/usuarios/editar/<%= usuario.getIdUsuario() %>">
    <label>Usuario <input name="usuario" value="<%= usuario.getUsuario() %>" required maxlength="45"></label><br>
    <label>Nueva contraseña <input type="password" name="password"></label><br>
    <label>Rol
        <select name="rol" required>
            <option value="cliente" <%= "cliente".equals(usuario.getRol()) ? "selected" : "" %>>cliente</option>
            <option value="vendedor" <%= "vendedor".equals(usuario.getRol()) ? "selected" : "" %>>vendedor</option>
            <option value="administrador" <%= "administrador".equals(usuario.getRol()) ? "selected" : "" %>>administrador</option>
        </select>
    </label><br>
    <button type="submit">Guardar</button>
</form>
<a href="${pageContext.request.contextPath}/tienda_informatica/usuarios">Volver</a>
</body>
</html>
