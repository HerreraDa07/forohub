package com.forohub.forohub.seguridad.autenticacion;

import com.forohub.forohub.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion implements UserDetailsService {
    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositorioUsuario.findByCorreo(username);
    }
}