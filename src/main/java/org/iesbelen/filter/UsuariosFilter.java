package org.iesbelen.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.iesbelen.model.Usuario;

@WebFilter(
        urlPatterns = {"/usuarios/*", "/tienda_informatica/usuarios/*"},
        initParams = @WebInitParam(name = "acceso-concedido-a-rol", value = "administrador")
)
public class UsuariosFilter implements Filter {

    private String rolAcceso;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUri = httpRequest.getRequestURI();
        String loginUri = httpRequest.getContextPath() + "/tienda_informatica/usuarios/login";
        if (requestUri.endsWith("/usuarios/login") || requestUri.endsWith("/usuarios/login/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession();
        Object usuarioEnSesion = session.getAttribute("usuario");
        if (usuarioEnSesion instanceof Usuario usuario && rolAcceso.equals(usuario.getRol())) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(loginUri);
    }
}
