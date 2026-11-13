package org.iesbelen.servlet;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.iesbelen.dao.UsuarioDAO;
import org.iesbelen.dao.UsuarioDAOImpl;
import org.iesbelen.model.Usuario;

@WebServlet(name = "usuariosServlet", value = {
        "/usuarios/*",
        "/tienda_informatica/usuarios/*"
})
public class UsuariosServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = normalize(request.getPathInfo());
        if (path.isEmpty()) {
            request.setAttribute("usuarios", usuarioDAO.getAll());
            forward(request, response, "/WEB-INF/jsp/usuario/usuario.jsp");
        } else if ("/crear".equals(path)) {
            forward(request, response, "/WEB-INF/jsp/usuario/crear-usuario.jsp");
        } else if ("/login".equals(path)) {
            forward(request, response, "/WEB-INF/jsp/usuario/login.jsp");
        } else if (path.startsWith("/editar/")) {
            forwardEdit(request, response, path);
        } else if (path.startsWith("/")) {
            forwardDetail(request, response, path);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = normalize(request.getPathInfo());
        if ("/login".equals(path)) {
            Optional<Usuario> usuario = usuarioDAO.authenticate(
                    request.getParameter("usuario"), request.getParameter("password"));
            if (usuario.isEmpty()) {
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                try {
                    forward(request, response, "/WEB-INF/jsp/usuario/login.jsp");
                } catch (ServletException exception) {
                    throw new IOException("No se pudo mostrar el login", exception);
                }
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario.get());
            response.sendRedirect(request.getContextPath() + "/usuarios");
            return;
        } else if ("/crear".equals(path)) {
            usuarioDAO.create(readUser(request, 0, true));
        } else if (path.startsWith("/editar/")) {
            usuarioDAO.update(readUser(request, parseId(path, "/editar/"), false));
        } else if (path.startsWith("/borrar/")) {
            usuarioDAO.delete(parseId(path, "/borrar/"));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/tienda_informatica/usuarios");
    }

    private void forwardEdit(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        int id = parseId(path, "/editar/");
        Optional<Usuario> usuario = usuarioDAO.find(id);
        if (usuario.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("usuario", usuario.get());
        forward(request, response, "/WEB-INF/jsp/usuario/editar-usuario.jsp");
    }

    private void forwardDetail(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        int id = parseId(path, "/");
        Optional<Usuario> usuario = usuarioDAO.find(id);
        if (usuario.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("usuario", usuario.get());
        forward(request, response, "/WEB-INF/jsp/usuario/detalle-usuario.jsp");
    }

    private Usuario readUser(HttpServletRequest request, int id, boolean passwordRequired) {
        String username = request.getParameter("usuario");
        String password = request.getParameter("password");
        String role = request.getParameter("rol");
        if (username == null || username.isBlank()
                || (passwordRequired && (password == null || password.isBlank()))
                || !isValidRole(role)) {
            throw new IllegalArgumentException("Datos de usuario no válidos");
        }
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setUsuario(username.trim());
        usuario.setPassword(password);
        usuario.setRol(role);
        return usuario;
    }

    private boolean isValidRole(String role) {
        return "cliente".equals(role) || "vendedor".equals(role) || "administrador".equals(role);
    }

    private int parseId(String path, String prefix) {
        try {
            return Integer.parseInt(path.substring(prefix.length()));
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("Identificador de usuario no válido", exception);
        }
    }

    private String normalize(String pathInfo) {
        if (pathInfo == null || "/".equals(pathInfo)) {
            return "";
        }
        return pathInfo.replaceAll("/+$", "");
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}
