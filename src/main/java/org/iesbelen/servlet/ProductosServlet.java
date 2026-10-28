package org.iesbelen.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.iesbelen.dao.ProductoDAO;
import org.iesbelen.dao.ProductoDAOImpl;
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
            request.setAttribute("listaProductos", productos);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
        } else {
            String[] pathParts = pathInfo.replaceAll("/$", "").split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-producto.jsp");
            } else if (pathParts.length == 2) {
                ProductoDAO productoDAO = new ProductoDAOImpl();
                try {
                    Optional<Producto> producto = productoDAO.find(Integer.parseInt(pathParts[1]));
                    producto.ifPresent(value -> request.setAttribute("producto", value));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");
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
        if (pathInfo == null || !pathInfo.replaceAll("/$", "").equals("/crear")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Producto producto = new Producto();
        producto.setNombre(request.getParameter("nombre"));
        new ProductoDAOImpl().create(producto);

        response.sendRedirect(request.getContextPath() + "/tienda_informatica/producto");
    }
}
