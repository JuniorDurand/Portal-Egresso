package egresso.demo.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contato_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEgresso {
    
    @EmbeddedId
    private ContatoEgressoId id;

    // arquivo CursoEgressoId 
    //tem uma variavel egressoId e uma cursoId

    @ManyToOne
    @MapsId("egressoId")
    @JoinColumn(name = "egresso_id")
    private Egresso egresso;

    @ManyToOne
    @MapsId("contatoId")
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @Column(name = "descricao")   
    private String descricao;
    
    
}
