package egresso.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="curso_egresso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoEgresso {
    
    @EmbeddedId
    private CursoEgressoId id;


    @ManyToOne
    @MapsId("egressoId")
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;
    
    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Column(name = "data_inicio")   
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataInicio;

    @Column(name = "data_conclusao")   
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataConclusao;

}
