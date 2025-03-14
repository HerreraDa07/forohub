package com.forohub.forohub.controlador;

import com.forohub.forohub.dominio.rol.Rol;
import com.forohub.forohub.dominio.usuario.Usuario;
import com.forohub.forohub.dominio.usuario.UsuarioRegistroDto;
import com.forohub.forohub.dominio.usuario.UsuarioRespuestaDto;
import com.forohub.forohub.repositorio.RepositorioRol;
import com.forohub.forohub.repositorio.RepositorioUsuario;
import com.forohub.forohub.seguridad.encriptador.Encriptador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/registro")
@SuppressWarnings("unused")
@Tag(name = "Registro", description = "Operación relacionada con el registro de usuarios")
public class ControladorRegistroUsuario {
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    RepositorioRol repositorioRol;
    @Autowired
    Encriptador encriptador;

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en la base de datos")
    public ResponseEntity<UsuarioRespuestaDto> registro(@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto, UriComponentsBuilder uriComponentsBuilder) {
        Rol rol = repositorioRol.findByNombre("Usuario");
        Usuario usuario = repositorioUsuario.save(new Usuario(usuarioRegistroDto, rol, encriptador));
        UsuarioRespuestaDto usuarioRespuestaDto = (new UsuarioRespuestaDto(usuario.getNombre(), usuarioRegistroDto.correo(), "Usuario creado satisfactoriamente"));
        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(usuarioRespuestaDto);
    }
}