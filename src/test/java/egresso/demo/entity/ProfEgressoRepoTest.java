package egresso.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class ProfEgressoRepoTest {

  @Autowired
  ProfEgressoRepo repository;
  @Autowired
  EgressoRepo repositoryEgresso;
  @Autowired
  FaixaSalarioRepo repositoryFaixaSalario;
  @Autowired
  CargoRepo repositoryCargo;

  private ProfEgresso createCenario(){
    Egresso egresso = Egresso.builder()
                        .nome("Teste")
                        .email("teste@teste.com")
                        .cpf("333.333.333-33")
                        .resumo("kk")
                        .urlFoto("http://")
                        .build();
    
    Cargo cargo = Cargo.builder()
                        .nome("Teste")
                        .descricao("Descricao teste")
                        .build();

    FaixaSalario faixaSalario = FaixaSalario.builder()
                                  .descricao("Descricao teste")
                                  .build();

    ProfEgresso profEgresso = ProfEgresso.builder()
                              .empresa("empresa teste")
                              .descricao("descricao teste")
                              .dataRegistro(LocalDate.of(2020, 1, 8))
                              .egresso(egresso)
                              .cargo(cargo)
                              .faixaSalario(faixaSalario)
                              .build();

    return profEgresso;
  }
  
  @Test
  public void deveVerificarSalvarProfEgresso() throws Exception {
    
    //cenário
    ProfEgresso entity = createCenario();

    //ação
    ProfEgresso salvo = repository.save(entity);

    //verificação
    Assertions.assertNotNull(salvo);
    Assertions.assertEquals(entity.getEmpresa(), salvo.getEmpresa());
    Assertions.assertEquals(entity.getDescricao(), salvo.getDescricao());
    Assertions.assertEquals(entity.getDataRegistro(), salvo.getDataRegistro());
  }

  @Test
  public void deveVerificarBuscarProfEgressoPorEgresso() throws Exception {
    
    //cenário
    ProfEgresso profEgresso = createCenario();
    
    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    

    ProfEgresso salvo = repository.save(profEgresso);
    
    //ação
    List<ProfEgresso> retList = repository.findByEgresso(egresso);
    ProfEgresso ret = retList.get(0);

    //verificação
    Assertions.assertNotNull(salvo);
    Assertions.assertEquals(profEgresso.getEmpresa(), ret.getEmpresa());
    Assertions.assertEquals(profEgresso.getDescricao(), ret.getDescricao());
    Assertions.assertEquals(profEgresso.getDataRegistro(), ret.getDataRegistro());
    Assertions.assertEquals(profEgresso.getEgresso().getId(), ret.getEgresso().getId());
    Assertions.assertEquals(profEgresso.getCargo().getId(), ret.getCargo().getId());
    Assertions.assertEquals(profEgresso.getFaixaSalario().getId(), ret.getFaixaSalario().getId());
  }

  @Test
  public void deveVerificarExisteProfEgressoPorEgresso() throws Exception {
    
    //cenário
    ProfEgresso profEgresso = createCenario();

    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    
    
    repository.save(profEgresso);
    
    //ação
    boolean ret = repository.existsByEgresso(egresso);

    //verificação
    Assertions.assertTrue(ret);
  }

  @Test
  public void deveVerificarDeleteProfEgressoPorEgresso() throws Exception {
    
    //cenário
    ProfEgresso profEgresso = createCenario();

    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    
    
    repository.save(profEgresso);
    
    //ação
    repository.deleteByEgresso(egresso);
    boolean ret = repository.existsByEgresso(egresso);

    //verificação
    Assertions.assertFalse(ret);
  }
}