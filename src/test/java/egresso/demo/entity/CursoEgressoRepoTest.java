package egresso.demo.entity;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class CursoEgressoRepoTest {
    
    @Autowired
    CursoEgressoRepo repository;
    @Autowired
    CursoRepo repositoryCurso;
    @Autowired
    EgressoRepo repositoryEgresso;


    private Curso createCenarioCurso(){
        return Curso.builder()
                        .nome("curso teste")
                        .nivel("nivel teste")
                        .build();
    }

    
    private Egresso createCenarioEgresso(){
        return Egresso.builder()
                        .nome("Egresso teste")
                        .cpf("333-333-333-33")
                        .email("pavani@mail.com")
                        .resumo("resumo teste")
                        .urlFoto("http://")
                        .build();
    }

    private CursoEgresso createCenarioCursoEgresso(Curso c, Egresso e){
        return CursoEgresso.builder()
                        .id(new CursoEgressoId(e.getId(), c.getId()))
                        .curso(c)
                        .egresso(e)
                        .dataConclusao(LocalDate.of(2005, 6, 12))
                        .dataInicio(LocalDate.of(2002, 6, 12))
                        .build();
    }


    @Test
    public void deveVerificarSalvarCursoEgresso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);

        // ação
        CursoEgresso salvo = repository.save(cursoEgresso);
        
        // verificação

        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cursoEgresso.getDataConclusao(), salvo.getDataConclusao());
        Assertions.assertEquals(cursoEgresso.getDataInicio(), salvo.getDataInicio());
        Assertions.assertEquals(cursoEgresso.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(cursoEgresso.getCurso().getId(), salvo.getCurso().getId());
    }

    @Test
    public void deveVerificarBuscarCursoEgressoPorEgresso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        CursoEgresso salvo = repository.save(cursoEgresso);

        // ação
        List<CursoEgresso> retList = repository.findByEgresso(salvo.getEgresso());
        CursoEgresso ret = retList.get(0);
        
        // verificação

        Assertions.assertEquals(cursoEgresso.getDataConclusao(), ret.getDataConclusao());
        Assertions.assertEquals(cursoEgresso.getDataInicio(), ret.getDataInicio());
        Assertions.assertEquals(cursoEgresso.getEgresso().getId(), ret.getEgresso().getId());
        Assertions.assertEquals(cursoEgresso.getCurso().getId(), ret.getCurso().getId());
    }

    @Test
    public void deveVerificarBuscarCursoEgressoPorCurso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        CursoEgresso salvo = repository.save(cursoEgresso);

        // ação
        List<CursoEgresso> retList = repository.findByCurso(salvo.getCurso());
        CursoEgresso ret = retList.get(0);
        
        // verificação

        Assertions.assertEquals(cursoEgresso.getDataConclusao(), ret.getDataConclusao());
        Assertions.assertEquals(cursoEgresso.getDataInicio(), ret.getDataInicio());
        Assertions.assertEquals(cursoEgresso.getEgresso().getId(), ret.getEgresso().getId());
        Assertions.assertEquals(cursoEgresso.getCurso().getId(), ret.getCurso().getId());
    }

    @Test
    public void deveVerificarExisteCursoEgressoPorEgresso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        CursoEgresso salvo = repository.save(cursoEgresso);

        // ação
        boolean ret = repository.existsByEgresso(salvo.getEgresso());
        
        // verificação

        Assertions.assertTrue(ret);
    }

    @Test
    public void deveVerificarExisteCursoEgressoPorCurso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        CursoEgresso salvo = repository.save(cursoEgresso);

        // ação
        boolean ret = repository.existsByCurso(salvo.getCurso());
        
        // verificação
        Assertions.assertTrue(ret);
    }

    @Test
    public void deveVerificarDeletarCursoEgressoPorEgresso() throws Exception {
        // cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repositoryCurso.save(curso);
        egresso = repositoryEgresso.save(egresso);
        
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        CursoEgresso salvo = repository.save(cursoEgresso);

        // ação
        repository.deleteByEgresso(salvo.getEgresso());
        boolean ret = repository.existsByEgresso(salvo.getEgresso());
        
        // verificação
        Assertions.assertFalse(ret);
    }


}
