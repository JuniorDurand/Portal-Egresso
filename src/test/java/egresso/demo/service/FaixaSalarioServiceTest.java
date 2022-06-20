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
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional 
public class FaixaSalarioServiceTest {

    @Autowired
    FaixaSalarioRepo repository;

    @Autowired
    EgressoRepo repository_egresso;

    @Autowired
    CargoRepo repository_cargo; 

    @Autowired
    ProfEgressoRepo repository_profEgresso; 
    

    @Autowired
    FaixaSalarioService service;

    private FaixaSalario createCenario(){
        FaixaSalario faixaSalario = FaixaSalario.builder()
            .descricao("Descricao teste")
            .build();
  
        return faixaSalario;
    }

    private Egresso createCenarioEgresso(){
        return Egresso.builder()
            .nome("Teste")
            .email("teste@teste.com")
            .cpf("333.333.333-33")
            .resumo("kk")
            .urlFoto("http://")
            .build();
    }

    private Cargo createCenarioCargo(){
        return Cargo.builder()
            .nome("Teste")
            .descricao("Descricao teste")
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
    void deveGerarErroFaixaSalarioNulo(){
        //cenário
        FaixaSalario faixaSalario = null;

        //ação e verificação
        Assertions.assertThrows(RegraNegocioRunTime.class, 
                () -> service.salvarFaixaSalario(faixaSalario),
                "Faixa de Salário inválido"
        );
    }

    @Test
    void deveVerificarQntEgressoPorFaixaSalario(){
        //cenário
        FaixaSalario faixaSalario = this.createCenario(); 
        faixaSalario = repository.save(faixaSalario);
        Egresso egresso = this.createCenarioEgresso();
        egresso = repository_egresso.save(egresso);
        Cargo cargo = this.createCenarioCargo();
        cargo = repository_cargo.save(cargo);
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        profEgresso = repository_profEgresso.save(profEgresso);
        
        //ação
        Long res = service.qntEgressoByFaixaSalario(faixaSalario);
        
        //verificação
        Assertions.assertEquals(1, res);
    }

    @Test
    public void deveVerificarSalvarFaixaSalario() throws Exception {
      //cenário
      FaixaSalario faixaSalario = this.createCenario();

      //ação
      FaixaSalario salvo = repository.save(faixaSalario);  //salva?
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(faixaSalario.getDescricao(), salvo.getDescricao());

      repository.delete(salvo);
    }
    
}
