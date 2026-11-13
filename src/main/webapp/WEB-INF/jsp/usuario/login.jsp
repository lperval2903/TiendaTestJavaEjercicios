<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Acceso</title></head>
<body>
<jsp:include page="/WEB-INF/jsp/fragmentos/header.jsp" />
<h1>Acceso de usuarios</h1>
<% if (request.getAttribute("error") != null) { %>
    <p><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="${pageContext.request.contextPath}/usuarios/login">
    <label>Usuario <input name="usuario" required></label><br>
    <label>Contraseña <input type="password" name="password" required></label><br>
    <button type="submit">Entrar</button>
</form>
<jsp:include page="/WEB-INF/jsp/fragmentos/footer.jsp" />
</body>
</html>
