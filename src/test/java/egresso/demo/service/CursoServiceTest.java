package egresso.demo.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.EgressoRepo;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoServiceTest {

    @Autowired
    CursoService service;

    @Autowired
    CursoRepo repository;

    @Autowired
    EgressoRepo repositoryEgresso;
    
    @Autowired
    CursoEgressoRepo repositoryCursoEgresso;

    private Curso createCenarioCurso(){
        return Curso.builder()
            .nome("Teste")
            .nivel("nivel teste")
            .build();
    }

    private Egresso generateEgresso(){
        return Egresso.builder()
            .nome("Egresso Teste")
            .email("teste@teste.com")
            .cpf("333.333.333-33")
            .resumo("teste")
            .urlFoto("http://")
            .build();
    }

    private CursoEgresso generateCursoEgresso(Curso c, Egresso e){
        return CursoEgresso.builder()
                        .id(new CursoEgressoId(e.getId(), c.getId()))
                        .curso(c)
                        .egresso(e)
                        .dataConclusao(LocalDate.of(2005, 6, 12))
                        .dataInicio(LocalDate.of(2002, 6, 12))
                        .build();
    }



    @Test
    void deveVerificarSalvarCurso(){
        //cenário
        Curso curso = createCenarioCurso();

        //ação
        Curso res = service.salvarCurso(curso);

        //verificação
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getId());
    }

    @Test
    public void deveVerificarBuscarEgressoPorCurso() {
      
      //cenário
      Curso curso = createCenarioCurso();
      Curso curso_salvo = repository.save(curso);
      Egresso egresso = generateEgresso();
      Egresso egresso_salvo = repositoryEgresso.save(egresso);
      CursoEgresso cursoEgresso = generateCursoEgresso(curso, egresso);
      CursoEgresso cursoEgresso_salvo = repositoryCursoEgresso.save(cursoEgresso);


      //ação 
      List<Egresso> retList = service.buscarEgressoPorCurso(curso_salvo);
      Egresso ret = retList.get(0);
      
      // verificação
      Assertions.assertNotNull(ret);
      Assertions.assertEquals(ret.getId(), egresso_salvo.getId());

    }
    
}
