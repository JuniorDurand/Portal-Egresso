
package egresso.demo.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EgressoServiceTest {

    @Autowired
    EgressoService service;
    
    @Autowired
    EgressoRepo repository;

    @Autowired
    CargoRepo repository_cargo;

    @Autowired
    FaixaSalarioRepo repository_faixaSalario;

    @Autowired
    CursoRepo repository_curso;

    @Autowired
    CursoEgressoRepo repository_cursoEgresso;

    private Egresso createCenario(){
        return Egresso.builder()
            .nome("Teste")
            .email("teste@teste.com")
            .cpf("333.333.333-33")
            .resumo("kk")
            .urlFoto("http://")
            .build();
    }
    private Curso createCenarioCurso(){
        return Curso.builder()
            .nome("Teste")
            .nivel("nivel teste")
            .build();
    }
    private Cargo createCenarioCargo(){
        return Cargo.builder()
            .nome("Teste")
            .descricao("Descricao teste")
            .build();
    }
    private FaixaSalario createCenarioFaixaSalario(){
        return FaixaSalario.builder()
            .descricao("Descricao teste")
            .build();
    }
    private CursoEgresso createCenarioCursoEgresso(Curso curso, Egresso egresso){
        return CursoEgresso.builder()
            .id(new CursoEgressoId(egresso.getId(), curso.getId()))
            .curso(curso)
            .egresso(egresso)
            .dataConclusao(LocalDate.of(2005, 6, 12))
            .dataInicio(LocalDate.of(2002, 6, 12))
            .build();
    }
    private ProfEgresso createCenarioProfEgresso(Egresso egresso, Cargo cargo, FaixaSalario faixaSalario){
        return ProfEgresso.builder()
            .empresa("empresa teste")
            .descricao("descricao teste")
            .dataRegistro(LocalDate.of(2020, 1, 8))
            .egresso(egresso)
            .cargo(cargo)
            .faixaSalario(faixaSalario)
            .build();
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemNome() {
        Egresso egresso = Egresso.builder()
            .email("teste@teste.com")
            .cpf("333.333.333-33")
            .resumo("kk")
            .urlFoto("http://")
            .build();
        Curso curso = this.createCenarioCurso();
        Cargo cargo = this.createCenarioCargo();
        FaixaSalario faixaSalario = this.createCenarioFaixaSalario();
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        List<CursoEgresso> cursos = new ArrayList();
        cursos.add(cursoEgresso);
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        List<ProfEgresso> profissoes = new ArrayList();
        profissoes.add(profEgresso);


        Assertions.assertThrows(RegraNegocioRunTime.class, 
                                    () -> service.salvarCadastro(egresso, cursos, profissoes), 
                                    "Nome do egresso deve ser informado");
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemCpf() {
        Egresso egresso = Egresso.builder()
            .nome("Teste")
            .email("teste@teste.com")
            .resumo("kk")
            .urlFoto("http://")
            .build();
        Curso curso = this.createCenarioCurso();
        Cargo cargo = this.createCenarioCargo();
        FaixaSalario faixaSalario = this.createCenarioFaixaSalario();
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        List<CursoEgresso> cursos = new ArrayList();
        cursos.add(cursoEgresso);
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        List<ProfEgresso> profissoes = new ArrayList();
        profissoes.add(profEgresso);
    
        Assertions.assertThrows(RegraNegocioRunTime.class, 
                                    () -> service.salvarCadastro(egresso, cursos, profissoes),
                                    "Cpf do egresso deve ser informado");   
    }

    @Test
    public void deveGerarErroSalvarComMesmoEmail() {
        Egresso egresso = this.createCenario();
        Curso curso = this.createCenarioCurso();
        curso = repository_curso.save(curso);
        Cargo cargo = this.createCenarioCargo();
        cargo = repository_cargo.save(cargo);
        FaixaSalario faixaSalario = this.createCenarioFaixaSalario();
        faixaSalario = repository_faixaSalario.save(faixaSalario);
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        cursoEgresso = repository_cursoEgresso.save(cursoEgresso); 
        List<CursoEgresso> cursos = new ArrayList();
        cursos.add(cursoEgresso);
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        List<ProfEgresso> profissoes = new ArrayList();
        profissoes.add(profEgresso);
        
        Egresso salvo = service.salvarCadastro(egresso, cursos, profissoes);
        Assertions.assertThrows(
                RegraNegocioRunTime.class, 
                        () -> service.salvarCadastro(egresso, cursos, profissoes)); 

        repository.delete(salvo);                                                      
    }

    @Test
    public void deveVerificarSalvarCadastro() {
        Egresso egresso = this.createCenario();
        Curso curso = this.createCenarioCurso();
        Cargo cargo = this.createCenarioCargo();
        FaixaSalario faixaSalario = this.createCenarioFaixaSalario();
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        List<CursoEgresso> cursos = new ArrayList();
        cursos.add(cursoEgresso);
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        List<ProfEgresso> profissoes = new ArrayList();
        profissoes.add(profEgresso);
        
        Egresso salvo = service.salvarCadastro(egresso, cursos, profissoes);
        
        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso.getCpf(), salvo.getCpf());

        repository.delete(salvo);                                                      
    }

    @Test
    public void deveVerificarremoverEgresso(){
        //cenário
        Egresso egresso = createCenario();
        Egresso egresso_salvo = repository.save(egresso);

        //ação
        service.removerEgresso(egresso_salvo);
        boolean res = repository.existsById(egresso_salvo.getId());

        //verificação
        Assertions.assertFalse(res);
    }

    @Test
    public void deveGerarErroRemover() {
        //cenario
        Egresso egresso = this.createCenario();
        Egresso egresso_salvo = repository.save(egresso);
        Curso curso = this.createCenarioCurso();
        Curso curso_salvo = repository_curso.save(curso);
        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        cursoEgresso.setEgresso(egresso_salvo);
        cursoEgresso.setCurso(curso_salvo);
        CursoEgresso cursoEgresso_salvo = repository_cursoEgresso.save(cursoEgresso);


        Assertions.assertThrows(RegraNegocioRunTime.class, 
                                    () -> service.removerEgresso(egresso_salvo),
                                    "Não pode remover pois está ligado a curso"); 
        

        repository_cursoEgresso.delete(cursoEgresso_salvo); 
        repository_curso.delete(curso_salvo);  
        repository.delete(egresso_salvo);                                                       
    }

    @Test
    public void deveRemoverEgresso() {
        //cenario
        Egresso egresso = this.createCenario();
        Egresso egresso_salvo = repository.save(egresso);

       //ação
       service.removerEgresso(egresso_salvo);
       boolean res = repository.existsById(egresso_salvo.getId());

       //verificação
       Assertions.assertFalse(res); 
        
  
        repository.delete(egresso_salvo);                                                       
    }
    
}
