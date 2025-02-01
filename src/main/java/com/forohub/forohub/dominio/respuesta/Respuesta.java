package com.forohub.forohub.dominio.respuesta;

import com.forohub.forohub.dominio.autor.Autor;
import com.forohub.forohub.dominio.topico.Topico;
import com.forohub.forohub.dominio.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne
    @JoinColumn(name = "id_topico")
    private Topico topico;
    private LocalDateTime fecha;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "nombre", column = @Column(name = "autor_nombre")), @AttributeOverride(name = "correo", column = @Column(name = "autor_correo"))})
    private Autor autor;
    private String solucion;

    public Respuesta(@Valid RespuestaRegistroDto respuestaRegistroDto, Topico topico, Usuario usuario) {
        this.mensaje = respuestaRegistroDto.mensaje();
        this.topico = topico;
        this.fecha = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.autor = new Autor(usuario.getNombre(), usuario.getCorreo());
        this.solucion = respuestaRegistroDto.solucion();
    }

    public void actualizar(@Valid RespuestaActualizarDto respuestaActualizarDto) {
        if (respuestaActualizarDto.mensaje() != null) {
            this.mensaje = respuestaActualizarDto.mensaje();
        }
        if (respuestaActualizarDto.solucion() != null) {
            this.solucion = respuestaActualizarDto.solucion();
        }
    }
}