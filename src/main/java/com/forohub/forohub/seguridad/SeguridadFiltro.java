package com.forohub.forohub.seguridad;

import com.forohub.forohub.repositorio.RepositorioUsuario;
import com.forohub.forohub.seguridad.autenticacion.ServicioToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@SuppressWarnings("unused")
public class SeguridadFiltro extends OncePerRequestFilter {
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    ServicioToken servicioToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var autorizacion = request.getHeader("Authorization");
        if (autorizacion != null) {
            var token = autorizacion.replace("Bearer ", "");
            var user = servicioToken.obtenerUsuario(token);
            if (user != null) {
                var usuario = repositorioUsuario.findByCorreo(user);
                var autenticacion = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacion);

            }
        }
        filterChain.doFilter(request, response);
    }
}