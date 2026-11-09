<html>
<head>
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <title>Inicio</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
   <style>
      #contenedor {
         display: flex;
         align-items: center;
         justify-content: center;
      }
   </style>
</head>
<body>

<div id="contenedor">
   <div class="card" style="width: 18rem;">
      <div class="card-body">
         <h5 class="card-title">Gestion de Fabricantes</h5>
         <p class="card-text">Accede a la gestion de fabricantes para crear, editar y eliminar fabricantes.</p>
         <a href="${pageContext.request.contextPath}/tienda_informatica/fabricantes" class="btn btn-primary">Ir a Fabricantes</a>
      </div>
   </div>
   <div class="card" style="width: 18rem; margin-left: 20px;">
      <div class="card-body">
         <h5 class="card-title">Gestion de Productos</h5>
         <p class="card-text">Accede a la gestion de productos para crear, editar y eliminar productos.</p>
         <a href="${pageContext.request.contextPath}/tienda_informatica/producto" class="btn btn-primary">Ir a Productos</a>
      </div>
   </div>
</div>