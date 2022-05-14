package egresso.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoEgressoId implements Serializable {

    @Column(name = "id_egresso")
    private Long egressoId;
    
    
    @Column(name = "id_curso")
    private Long cursoId;
}
