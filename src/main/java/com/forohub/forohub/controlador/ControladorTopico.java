package com.forohub.forohub.controlador;

import com.forohub.forohub.dominio.autor.AutorDto;
import com.forohub.forohub.dominio.curso.Curso;
import com.forohub.forohub.dominio.curso.CursoDto;
import com.forohub.forohub.dominio.topico.*;
import com.forohub.forohub.dominio.usuario.Usuario;
import com.forohub.forohub.repositorio.RepositorioCurso;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SuppressWarnings("unused")
@Tag(name = "Tópicos", description = "Operaciones relacionadas con los tópicos")
public class ControladorTopico {
    @Autowired
    RepositorioTopico repositorioTopico;
    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    RepositorioCurso repositorioCurso;
    @Autowired
    ServicioToken servicioToken;

    @GetMapping
    @Operation(summary = "Listado de tópicos", description = "Hace un listado con los tópicos registrados en la base de datos")
    public ResponseEntity<Page<TopicoListadoDto>> listado(@PageableDefault(size = 5, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(repositorioTopico.findAll(pageable).map(TopicoListadoDto::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Datos tópico", description = "Trae los datos de un tópico registrado en la base de datos")
    public ResponseEntity<TopicoRespuestaDto> topico(@PathVariable Long id) {
        Topico topico = repositorioTopico.getReferenceById(id);
        var datos = new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria()));
        return ResponseEntity.ok(datos);
    }

    @PostMapping
    @Operation(summary = "Registrar tópico", description = "Registra los datos de un tópico en la base de datos")
    public ResponseEntity<TopicoRespuestaDto> registro(@RequestBody @Valid TopicoRegistroDto topicoRegistroDto, UriComponentsBuilder uriComponentsBuilder, @RequestHeader("Authorization") String autorizacion) {
        String token = autorizacion.replace("Bearer ", "");
        String correo = servicioToken.obtenerCorreo(token);
        Usuario usuario = (Usuario) repositorioUsuario.findByCorreo(correo);
        Curso curso = repositorioCurso.findById(topicoRegistroDto.curso()).orElseThrow(() -> new RuntimeException("El curso ingresado no fue encontrado"));
        Topico topico = repositorioTopico.save(new Topico(topicoRegistroDto, usuario, curso));
        TopicoRespuestaDto topicoRespuestaDto = (new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria())));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(topicoRespuestaDto);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualizar tópico", description = "Actualiza los datos de un tópico en la base de datos")
    public ResponseEntity<TopicoRespuestaDto> actualizar(@RequestBody @Valid TopicoActualizarDto topicoActualizarDto) {
        Optional<Topico> optionalTopico = repositorioTopico.findById(topicoActualizarDto.id());
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.actualizar(topicoActualizarDto);
            return ResponseEntity.ok(new TopicoRespuestaDto(topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getEstatus(), new AutorDto(topico.getAutor().getNombre(), topico.getAutor().getCorreo()), new CursoDto(topico.getCurso().getNombre(), topico.getCurso().getCategoria())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar tópico", description = "Elimina un tópico de la base de datos")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Topico> optionalTopico = repositorioTopico.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            repositorioTopico.delete(topico);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}