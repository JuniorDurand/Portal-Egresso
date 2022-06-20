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

import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class EgressoRepoTest {

    @Autowired
    EgressoRepo repository;

    @Autowired
    ProfEgressoRepo repositoryProfEgresso;
    @Autowired
    CargoRepo repositoryCargo;

    private Egresso createCenario(){
      Egresso egresso = Egresso.builder()
                                .nome("Teste")
                                .email("teste@teste.com")
                                .cpf("333.333.333-33")
                                .resumo("kk")
                                .urlFoto("http://")
                                .build();

      return egresso;
    }

    private ProfEgresso createCenarioProfEgresso(){
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


      ProfEgresso profEgresso = ProfEgresso.builder()
                  .empresa("empresa teste")
                  .descricao("descricao teste")
                  .dataRegistro(LocalDate.of(2020, 1, 8))
                  .egresso(egresso)
                  .cargo(cargo)
                  // .faixaSalario(faixaSalario)
                  .build();

      return profEgresso;
    }
    
    @Test
    public void deveVerificarSalvarEgresso() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);

      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(entity.getNome(), salvo.getNome());
      Assertions.assertEquals(entity.getEmail(), salvo.getEmail());
      Assertions.assertEquals(entity.getCpf(), salvo.getCpf());
      Assertions.assertEquals(entity.getResumo(), salvo.getResumo());
      Assertions.assertEquals(entity.getUrlFoto(), salvo.getUrlFoto());

      repository.delete(salvo);
    }


    @Test
    public void deveVerificarBuscarEgressoPorEmail() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      List<Egresso> retList = repository.findByEmail(entity.getEmail());

      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Egresso ret = retList.get(0);

      Assertions.assertEquals(entity.getNome(), ret.getNome());
      Assertions.assertEquals(entity.getEmail(), ret.getEmail());
      Assertions.assertEquals(entity.getCpf(), ret.getCpf());
      Assertions.assertEquals(entity.getResumo(), ret.getResumo());
      Assertions.assertEquals(entity.getUrlFoto(), ret.getUrlFoto());

      repository.delete(salvo);
    }


    @Test
    public void deveVerificarExisteEgressoPorEmail() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      boolean ret = repository.existsByEmail(entity.getEmail());

      // verificação
      Assertions.assertTrue(ret);

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarDeletarEgressoPorEmail() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      repository.deleteByEmail(salvo.getEmail());
      boolean ret = repository.existsByEmail(salvo.getEmail());

      // verificação
      Assertions.assertFalse(ret);

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarBuscarEgressoPorCpf() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      List<Egresso> retList = repository.findByCpf(entity.getCpf());

      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Egresso ret = retList.get(0);

      Assertions.assertEquals(entity.getNome(), ret.getNome());
      Assertions.assertEquals(entity.getEmail(), ret.getEmail());
      Assertions.assertEquals(entity.getCpf(), ret.getCpf());
      Assertions.assertEquals(entity.getResumo(), ret.getResumo());
      Assertions.assertEquals(entity.getUrlFoto(), ret.getUrlFoto());

      repository.delete(salvo);
    }


    @Test
    public void deveVerificarExisteEgressoPorCpf() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      boolean ret = repository.existsByCpf(entity.getCpf());

      // verificação
      Assertions.assertTrue(ret);

      repository.delete(salvo);
    }

    @Test
    @Transactional
    public void deveVerificarDeletarEgressoPorCpf() throws Exception {
      
      //cenário
      Egresso entity = createCenario();

      //ação
      Egresso salvo = repository.save(entity);
      repository.deleteByCpf(entity.getCpf());
      boolean ret = repository.existsByCpf(entity.getCpf());

      // verificação
      Assertions.assertFalse(ret);

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarContarEgressoPorCargo() throws Exception {
      
      //cenário
      ProfEgresso profEgresso = createCenarioProfEgresso();
      Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
      repository.save(profEgresso.getEgresso());
      profEgresso = repositoryProfEgresso.save(profEgresso);

      //ação

      Long ret = repository.countEgressoByCargo(cargo);

      // verificação
      Assertions.assertEquals(1, ret);
    }
}