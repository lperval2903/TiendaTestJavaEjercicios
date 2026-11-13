<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Tienda informática</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
         rel="stylesheet"
         integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
         crossorigin="anonymous">
   <style>
      body {
         min-height: 100vh;
         background: #f4f7fb;
      }
      .hero {
         background: linear-gradient(135deg, #0d6efd, #173b75);
         color: white;
      }
      .menu-card {
         height: 100%;
         border: 0;
         transition: transform .2s, box-shadow .2s;
      }
      .menu-card:hover {
         transform: translateY(-4px);
         box-shadow: 0 .75rem 1.5rem rgba(0, 0, 0, .12);
      }
   </style>
</head>
<body>
<header class="hero py-5">
   <div class="container">
      <div class="d-flex justify-content-between align-items-center">
         <div>
            <p class="text-uppercase fw-semibold mb-2 opacity-75">Panel de gestión</p>
            <h1 class="display-5 fw-bold mb-2">Tienda informática</h1>
            <p class="lead mb-0">Administra fabricantes, productos y usuarios desde un solo lugar.</p>
         </div>
         <a href="${pageContext.request.contextPath}/tienda_informatica/usuarios/login"
            class="btn btn-light btn-lg">Iniciar sesión</a>
      </div>
   </div>
</header>

<main class="container py-5">
   <div class="row g-4">
      <div class="col-md-4">
         <article class="card menu-card shadow-sm">
            <div class="card-body p-4 d-flex flex-column">
               <span class="badge text-bg-primary align-self-start mb-3">Catálogo</span>
               <h2 class="h4">Fabricantes</h2>
               <p class="text-secondary flex-grow-1">Consulta y administra los fabricantes de la tienda.</p>
               <a href="${pageContext.request.contextPath}/tienda_informatica/fabricantes"
                  class="btn btn-primary">Ver fabricantes</a>
            </div>
         </article>
      </div>
      <div class="col-md-4">
         <article class="card menu-card shadow-sm">
            <div class="card-body p-4 d-flex flex-column">
               <span class="badge text-bg-success align-self-start mb-3">Catálogo</span>
               <h2 class="h4">Productos</h2>
               <p class="text-secondary flex-grow-1">Consulta los productos disponibles y su información.</p>
               <a href="${pageContext.request.contextPath}/tienda_informatica/producto"
                  class="btn btn-success">Ver productos</a>
            </div>
         </article>
      </div>
      <div class="col-md-4">
         <article class="card menu-card shadow-sm">
            <div class="card-body p-4 d-flex flex-column">
               <span class="badge text-bg-dark align-self-start mb-3">Administración</span>
               <h2 class="h4">Usuarios</h2>
               <p class="text-secondary flex-grow-1">Accede al mantenimiento de usuarios con tu cuenta.</p>
               <a href="${pageContext.request.contextPath}/tienda_informatica/usuarios/login"
                  class="btn btn-dark">Iniciar sesión</a>
            </div>
         </article>
      </div>
   </div>
</main>
</body>
</html>
