package com.forohub.forohub.dominio.usuario;

import com.forohub.forohub.dominio.rol.Rol;
import com.forohub.forohub.seguridad.encriptador.Encriptador;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo;
    private String clave;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    public Usuario(@Valid UsuarioRegistroDto usuarioRegistroDto, Rol rol, Encriptador encriptador) {
        this.nombre = usuarioRegistroDto.nombre();
        this.correo = usuarioRegistroDto.correo();
        this.clave = encriptador.bcrypt(usuarioRegistroDto.clave());
        this.rol = rol;
    }

    public void actualizar(@Valid UsuarioActualizarDto usuarioActualizarDto, Encriptador encriptador) {
        if (usuarioActualizarDto.nombre() != null) {
            this.nombre = usuarioActualizarDto.nombre();
        }
        if (usuarioActualizarDto.correo() != null) {
            this.correo = usuarioActualizarDto.correo();
        }
        if (usuarioActualizarDto.clave() != null) {
            this.clave = encriptador.bcrypt(usuarioActualizarDto.clave());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROL_USER"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}