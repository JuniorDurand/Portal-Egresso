package egresso.demo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {
    
    @Id
    @Column(name= "id_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nome")
    private String nome;

    @Column(name= "nivel")
    private String nivel;

    // @ManyToMany(mappedBy = "cursos")
    // Set<Egresso> egressos;
    @OneToMany(mappedBy = "curso")
    private Set<CursoEgresso> cursoEgresso;
}
