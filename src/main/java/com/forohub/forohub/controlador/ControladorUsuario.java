package com.forohub.forohub.controlador;

import com.forohub.forohub.dominio.autor.AutorDto;
import com.forohub.forohub.dominio.usuario.Usuario;
import com.forohub.forohub.dominio.usuario.UsuarioActualizarDto;
import com.forohub.forohub.dominio.usuario.UsuarioListadoDto;
import com.forohub.forohub.dominio.usuario.UsuarioRespuestaDto;
import com.forohub.forohub.repositorio.RepositorioUsuario;
import com.forohub.forohub.seguridad.autenticacion.ServicioToken;
import com.forohub.forohub.seguridad.encriptador.Encriptador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@SuppressWarnings("unused")
@Tag(name = "Usuario", description = "Operaciones relacionadas con el usuario")
public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    Encriptador encriptador;
    @Autowired
    ServicioToken servicioToken;

    @GetMapping
    @Operation(summary = "Listado de usuarios", description = "Hace un listado con los usuarios registrados en la base de datos")
    public ResponseEntity<Page<UsuarioListadoDto>> listado(Pageable pageable) {
        return ResponseEntity.ok(repositorioUsuario.findAll(pageable).map(UsuarioListadoDto::new));
    }

    @GetMapping("/obtener")
    public ResponseEntity<AutorDto> obtenerUsuario(@RequestHeader("Authorization") String jwt) {
        String token = jwt.replace("Bearer ", "");
        String correo = servicioToken.obtenerCorreo(token);
        Usuario usuario = (Usuario) repositorioUsuario.findByCorreo(correo);
        AutorDto autor = new AutorDto(usuario.getNombre(), usuario.getCorreo());
        return ResponseEntity.ok(autor);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario en la base de datos")
    public ResponseEntity<UsuarioRespuestaDto> actualizar(@RequestBody @Valid UsuarioActualizarDto usuarioActualizarDto) {
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(usuarioActualizarDto.id());
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.actualizar(usuarioActualizarDto, encriptador);
            return ResponseEntity.ok(new UsuarioRespuestaDto(usuario.getNombre(), usuario.getCorreo(), "El usuario ha sido actualizado satisfactoriamente"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario de la base de datos")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            repositorioUsuario.delete(usuario);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}