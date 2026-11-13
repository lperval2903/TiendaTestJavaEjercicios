<style>
    .site-header { background: #172033; color: #fff; padding: 1rem 0; margin-bottom: 1.5rem; }
    .site-header nav { max-width: 900px; margin: auto; display: flex; align-items: center; gap: 1.5rem; }
    .site-header a { color: #fff; text-decoration: none; }
    .site-header .brand { font-size: 1.2rem; font-weight: bold; margin-right: auto; }
    .site-header .nav-link { opacity: .85; }
    .site-header .nav-link:hover { opacity: 1; text-decoration: underline; }
</style>
<header class="site-header">
    <nav class="navbar navbar-expand-lg navbar-dark container">
        <a class="brand" href="${pageContext.request.contextPath}/">Tienda informatica</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/tienda_informatica/fabricantes">Fabricantes</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/tienda_informatica/producto">Productos</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/tienda_informatica/usuarios/login">Usuarios</a>
    </nav>
</header>
