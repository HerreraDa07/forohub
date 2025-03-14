package com.forohub.forohub.controlador;

import com.forohub.forohub.dominio.autor.AutorDto;
import com.forohub.forohub.dominio.curso.CursoDto;
import com.forohub.forohub.dominio.respuesta.*;
import com.forohub.forohub.dominio.topico.Topico;
import com.forohub.forohub.dominio.topico.TopicoRespuestaDto;
import com.forohub.forohub.dominio.topico.TopicoUsuarioDto;
import com.forohub.forohub.dominio.usuario.Usuario;
import com.forohub.forohub.repositorio.RepositorioRespuesta;
import com.forohub.forohub.repositorio.RepositorioTopico;
import com.forohub.forohub.repositorio.RepositorioUsuario;
import com.forohub.forohub.seguridad.autenticacion.ServicioToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@SuppressWarnings("unused")
@Tag(name = "Respuestas", description = "Operaciones relacionadas con las respuestas")
public class ControladorRespuesta {
    @Autowired
    RepositorioRespuesta repositorioRespuesta;
    @Autowired
    ServicioToken servicioToken;
    @Autowired
    RepositorioTopico repositorioTopico;
    @Autowired
    RepositorioUsuario repositorioUsuario;

    @GetMapping
    @Operation(summary = "Listado de respuestas", description = "Hace un listado con las respuestas registradas en la base de datos")
    public ResponseEntity<Page<RespuestaListadoDto>> listado(Pageable pageable) {
        return ResponseEntity.ok(repositorioRespuesta.findAll(pageable).map(RespuestaListadoDto::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Datos respuesta", description = "Trae los datos de una respuesta registrada en la base de datos")
    public ResponseEntity<RespuestaRespuestaDto> respuesta(@PathVariable Long id) {
        Respuesta respuesta = repositorioRespuesta.getReferenceById(id);
        Topico topico = respuesta.getTopico();
        var datos = new RespuestaRespuestaDto(respuesta.getId(), respuesta.getMensaje(), new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria())), respuesta.getFecha(), new AutorDto(respuesta.getAutor().getNombre(), respuesta.getAutor().getCorreo()), respuesta.getSolucion());
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<RespuestaUsuarioDto>> respuestas(@PathVariable @Valid String usuario) {
        List<Respuesta> respuestas = repositorioRespuesta.findByAutorNombre(usuario);
        List<RespuestaUsuarioDto> respuestaUsuarioDto = respuestas.stream().map(r -> new RespuestaUsuarioDto(r.getId(), r.getMensaje(), r.getSolucion(), r.getFecha(), new TopicoUsuarioDto(r.getTopico().getTitulo(), r.getTopico().getMensaje(), new AutorDto(r.getTopico().getAutor().getNombre(), r.getTopico().getAutor().getCorreo())))).toList();
        return ResponseEntity.ok(respuestaUsuarioDto);
    }

    @PostMapping
    @Operation(summary = "Registrar respuesta", description = "Registra los datos de una respuesta en la base de datos")
    public ResponseEntity<RespuestaRespuestaDto> registro(@RequestBody @Valid RespuestaRegistroDto respuestaRegistroDto, UriComponentsBuilder uriComponentsBuilder, @RequestHeader("Authorization") String autorizacion) {
        String token = autorizacion.replace("Bearer ", "");
        String correo = servicioToken.obtenerCorreo(token);
        Topico topico = repositorioTopico.findById(respuestaRegistroDto.topico()).orElseThrow(() -> new RuntimeException("El tópico ingresado no ha sido encontrado"));
        Usuario usuario = (Usuario) repositorioUsuario.findByCorreo(correo);
        Respuesta respuesta = repositorioRespuesta.save(new Respuesta(respuestaRegistroDto, topico, usuario));
        RespuestaRespuestaDto respuestaRespuestaDto = (new RespuestaRespuestaDto(respuesta.getId(), respuesta.getMensaje(), new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria())), respuesta.getFecha(), new AutorDto(respuesta.getAutor().getNombre(), respuesta.getAutor().getCorreo()), respuesta.getSolucion()));
        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(respuestaRespuestaDto);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualizar respuesta", description = "Actualiza los datos de una respuesta en la base de datos")
    public ResponseEntity<RespuestaRespuestaDto> actualizar(@RequestBody @Valid RespuestaActualizarDto respuestaActualizarDto) {
        Optional<Respuesta> optionalRespuesta = repositorioRespuesta.findById(respuestaActualizarDto.id());
        if ((optionalRespuesta.isPresent())) {
            Respuesta respuesta = optionalRespuesta.get();
            Topico topico = repositorioTopico.findById(respuesta.getTopico().getId()).orElseThrow(() -> new RuntimeException("El tópico ingresado no ha sido encontrado"));
            respuesta.actualizar(respuestaActualizarDto);
            return ResponseEntity.ok(new RespuestaRespuestaDto(respuesta.getId(), respuesta.getMensaje(), new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria())), respuesta.getFecha(), new AutorDto(respuesta.getAutor().getNombre(), respuesta.getAutor().getCorreo()), respuesta.getSolucion()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar respuesta", description = "Elimina una respuesta de la base de datos")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Respuesta> optionalRespuesta = repositorioRespuesta.findById(id);
        if (optionalRespuesta.isPresent()) {
            Respuesta respuesta = optionalRespuesta.get();
            repositorioRespuesta.delete(respuesta);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}