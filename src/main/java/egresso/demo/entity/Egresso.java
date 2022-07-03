package egresso.demo.entity;

import java.util.Set;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import javax.persistence.JoinColumn;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="egresso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Egresso {

    @Id
    @Column(name= "id_egresso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name= "nome")
    private String nome;
    
    @Column(name= "email")
    private String email;

    @Column(name= "cpf")
    private String cpf;

    @Column(name= "resumo")
    private String resumo;

    @Column(name= "url_foto")
    private String urlFoto;



    // @ManyToMany
    // @JoinTable(
    //   name = "curso_egresso",  
    //   joinColumns = {@JoinColumn(name = "id_curso")}, 
    //   inverseJoinColumns = @JoinColumn(name = "id_egresso"))
    //   Set<Contato> cursos;

    @OneToMany(mappedBy = "egresso")
    private Set<CursoEgresso> cursoEgresso;

    // relação many to many com contato (curso egresso no meio)
    @OneToMany(mappedBy = "egresso")
    private List<ContatoEgresso> contatoEgresso;

    @OneToMany(mappedBy = "egresso")
    private List<Depoimento> depoimentos;

    @OneToMany(mappedBy = "egresso")
    private List<ProfEgresso> ProfEgressos;
}
