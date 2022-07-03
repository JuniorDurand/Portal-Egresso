package egresso.demo.entity.dto;


import java.util.List;

import egresso.demo.entity.ProfEgresso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CargoDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private List<ProfEgresso> ProfEgressos;
}
