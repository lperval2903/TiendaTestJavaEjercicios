package org.iesbelen.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.iesbelen.dao.ProductoDAO;
import org.iesbelen.dao.ProductoDAOImpl;
import org.iesbelen.dao.FabricanteDAO;
import org.iesbelen.dao.FabricanteDAOImpl;
import org.iesbelen.model.Producto;

@WebServlet(name = "productosServlet", value = "/tienda_informatica/producto/*")
public class ProductosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo == null || "/".equals(pathInfo)) {
            ProductoDAO productoDAO = new ProductoDAOImpl();
            List<Producto> productos = productoDAO.getAll();
            String busqueda = request.getParameter("busqueda");
            if (busqueda == null) {
                busqueda = "";
            }
            busqueda = busqueda.trim();
            if (!busqueda.isEmpty()) {
                String textoBusqueda = busqueda.toLowerCase(Locale.ROOT);
                productos.removeIf(producto -> producto.getNombre() == null
                        || !producto.getNombre().toLowerCase(Locale.ROOT).contains(textoBusqueda));
            }
            request.setAttribute("listaProductos", productos);
            request.setAttribute("busqueda", busqueda);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/producto/productos.jsp");
        } else {
            String[] pathParts = pathInfo.replaceAll("/$", "").split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();
                request.setAttribute("listaFabricantes", fabricanteDAO.getAll());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/producto/crear-producto.jsp");
            } else if (pathParts.length == 2) {
                ProductoDAO productoDAO = new ProductoDAOImpl();
                try {
                    Optional<Producto> producto = productoDAO.find(Integer.parseInt(pathParts[1]));
                    producto.ifPresent(value -> request.setAttribute("producto", value));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/producto/detalle-producto.jsp");
                } catch (NumberFormatException exception) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String normalizedPath = pathInfo == null ? "" : pathInfo.replaceAll("/+$", "");
        if (!"/crear".equals(normalizedPath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El nombre es obligatorio");
            return;
        }

        Producto producto = new Producto();
        producto.setNombre(nombre.trim());
        String codigoFabricante = request.getParameter("codigoFabricantes");
        if (codigoFabricante == null || codigoFabricante.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Debe seleccionar un fabricante");
            return;
        }
        try {
            producto.setCodigoFabricante(Integer.parseInt(codigoFabricante));
        } catch (NumberFormatException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Fabricante no válido");
            return;
        }
        new ProductoDAOImpl().create(producto);

        response.sendRedirect(request.getContextPath() + "/tienda_informatica/producto");
    }
}
