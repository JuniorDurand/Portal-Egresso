package egresso.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FaixaSalarioDTO {

    private Long id;
    private String descricao;
}
