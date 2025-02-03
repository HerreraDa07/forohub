package com.forohub.forohub.controlador;

import com.forohub.forohub.dominio.usuario.Usuario;
import com.forohub.forohub.dominio.usuario.UsuarioDto;
import com.forohub.forohub.seguridad.autenticacion.JsonWebTokenDto;
import com.forohub.forohub.seguridad.autenticacion.ServicioToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acceso")
@SuppressWarnings("unused")
@Tag(name = "Acceso", description = "Operación relacionada con el acceso de los usuarios")
public class ControladorAcceso {
    @Autowired
    private ServicioToken servicioToken;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "Obtener acceso", description = "Solicita acceso por medio de autenticación y devuelve un token")
    public ResponseEntity<JsonWebTokenDto> acceso(@RequestBody @Valid UsuarioDto usuarioDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioDto.correo(), usuarioDto.clave());
        var autenticacion = authenticationManager.authenticate(authentication);
        var jwt = servicioToken.obtenerToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new JsonWebTokenDto(jwt));
    }
}