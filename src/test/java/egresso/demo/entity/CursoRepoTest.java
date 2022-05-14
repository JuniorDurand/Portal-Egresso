package egresso.demo.entity;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import egresso.demo.entity.repository.CursoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class CursoRepoTest {

    @Autowired
    CursoRepo repository;
    
    @Test
    public void deveVerificarSalvarCurso() throws Exception {
      
      //cenário
      Curso curso = Curso.builder()
                            .nome("Teste")
                            .nivel("nivel teste")
                            .build();
      //ação
       
      Curso salvo = repository.save(curso);  //salva?
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(curso.getNome(), salvo.getNome());
      Assertions.assertEquals(curso.getNivel(), salvo.getNivel());

      repository.delete(salvo);
    }


    @Test
    public void deveVerificarBuscaCursoPorNome() throws Exception {
      
      //cenário
      Curso curso = Curso.builder()
                      .nome("Teste")
                      .nivel("nivel teste")
                      .build();

       
      Curso salvo = repository.save(curso);
                      
      //ação
      List<Curso> ret = repository.findByNome("Teste");
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertFalse(ret.isEmpty());

      Curso retCurso = ret.get(0);


      Assertions.assertEquals(salvo.getId(), retCurso.getId());
      Assertions.assertEquals(salvo.getNome(), retCurso.getNome());
      Assertions.assertEquals(salvo.getNivel(), retCurso.getNivel());

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarExisteCursoPorNome() throws Exception {
      
      //cenário
      Curso curso = Curso.builder()
                      .nome("Teste")
                      .nivel("nivel teste")
                      .build();

       
      Curso salvo = repository.save(curso);
                      
      //ação
      boolean ret = repository.existsByNome("Teste");
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertTrue(ret);
    }

    @Test
    public void deveVerificarDeletarCursoPorNome() throws Exception {
      
      //cenário
      Curso curso = Curso.builder()
                      .nome("Teste")
                      .nivel("nivel teste")
                      .build();

       
      repository.save(curso);
                      
      //ação
      repository.deleteByNome("Teste");
      
      //verificação
      List<Curso> ret = repository.findByNome("Teste");
      Assertions.assertTrue(ret.isEmpty());
    }
}