package egresso.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="depoimento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Depoimento {
    @Id
    @Column(name= "id_depoimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    @Column(name= "texto")
    private String texto;

    @Column(name = "data")   
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;
}
