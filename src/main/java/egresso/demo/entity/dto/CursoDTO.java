package egresso.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CursoDTO {

    private Long id;
    private String nome;
    private String nivel;
}
