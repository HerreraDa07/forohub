package com.forohub.forohub.dominio.topico;

import com.forohub.forohub.dominio.autor.Autor;
import com.forohub.forohub.dominio.curso.Curso;
import com.forohub.forohub.dominio.respuesta.Respuesta;
import com.forohub.forohub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private String estatus;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "nombre", column = @Column(name = "autor_nombre")), @AttributeOverride(name = "correo", column = @Column(name = "autor_correo"))})
    private Autor autor;
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuesta;

    public Topico(@Valid TopicoRegistroDto topicoRegistroDto, Usuario usuario, Curso curso) {
        this.titulo = topicoRegistroDto.titulo();
        this.mensaje = topicoRegistroDto.mensaje();
        this.fecha = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.estatus = topicoRegistroDto.estatus();
        this.autor = new Autor(usuario.getNombre(), usuario.getCorreo());
        this.curso = curso;
    }

    public void actualizar(@Valid TopicoActualizarDto topicoActualizarDto) {
        if (topicoActualizarDto.titulo() != null) {
            this.titulo = topicoActualizarDto.titulo();
        }
        if (topicoActualizarDto.mensaje() != null) {
            this.mensaje = topicoActualizarDto.mensaje();
        }
        if (topicoActualizarDto.estatus() != null) {
            this.estatus = topicoActualizarDto.estatus();
        }
    }
}