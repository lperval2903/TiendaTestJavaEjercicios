<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Crear usuario</title></head>
<body>
<jsp:include page="/WEB-INF/jsp/fragmentos/header.jsp" />
<h1>Crear usuario</h1>
<form method="post" action="${pageContext.request.contextPath}/tienda_informatica/usuarios/crear">
    <label>Usuario <input name="usuario" required maxlength="45"></label><br>
    <label>Contraseña <input type="password" name="password" required></label><br>
    <label>Rol
        <select name="rol" required>
            <option value="cliente">cliente</option>
            <option value="vendedor">vendedor</option>
            <option value="administrador">administrador</option>
        </select>
    </label><br>
    <button type="submit">Crear</button>
</form>
<a href="${pageContext.request.contextPath}/tienda_informatica/usuarios">Volver</a>
<jsp:include page="/WEB-INF/jsp/fragmentos/footer.jsp" />
</body>
</html>
