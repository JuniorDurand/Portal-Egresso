package egresso.demo.entity.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DepoimentoDTO {
    
    private String texto;
    private LocalDate data;
    private Long idEgresso;
}
