package egresso.demo.entity;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato {
    
    @Id
    @Column(name= "id_contato")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        
    @Column(name= "nome")
    private String nome;
    
    @Column(name= "url_logo")
    private String urlLogo;

    @ManyToMany(mappedBy = "contatos")
    Set<Egresso> Egressos;
}
