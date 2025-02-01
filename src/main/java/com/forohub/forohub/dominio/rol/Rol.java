package com.forohub.forohub.dominio.rol;

import com.forohub.forohub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    public Rol(RolDto rolDto) {
        this.nombre = rolDto.nombre();
    }
}