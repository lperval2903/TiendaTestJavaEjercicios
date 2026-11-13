package org.iesbelen.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.iesbelen.model.Usuario;

@WebFilter(urlPatterns = {
        "/tienda_informatica/fabricantes/*",
        "/tienda_informatica/producto/*"
})
public class CatalogoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Usuario usuario = (Usuario) httpRequest.getSession().getAttribute("usuario");
        String path = httpRequest.getPathInfo();

        if (usuario == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/tienda_informatica/usuarios/login");
            return;
        }

        boolean esAdministrador = "administrador".equals(usuario.getRol());
        boolean esVendedor = "vendedor".equals(usuario.getRol());
        boolean esOperacionCrud = esCrudPath(path);
        boolean esFabricante = httpRequest.getRequestURI().contains("/fabricantes/");
        boolean esProducto = httpRequest.getRequestURI().contains("/producto/");

        if (esOperacionCrud && esFabricante && !esAdministrador) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (esOperacionCrud && esProducto && !esAdministrador && !esVendedor) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean esCrudPath(String path) {
        return path != null && (path.startsWith("/crear")
                || path.startsWith("/editar") || path.startsWith("/borrar"));
    }
}
