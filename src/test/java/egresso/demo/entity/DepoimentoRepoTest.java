package egresso.demo.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.entity.repository.EgressoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class DepoimentoRepoTest {

    @Autowired
    DepoimentoRepo repository;
    @Autowired
    EgressoRepo repositoryEgresso;

    private Depoimento createCenario(){
      Depoimento depoimento = Depoimento.builder()
          .texto("texto")
          .data(LocalDate.of(2020, 1, 8))
          .build();

      return depoimento;
    }

    private Egresso createCenarioEgresso(){
    Egresso egresso = Egresso.builder()
          .nome("Teste")
          .email("teste@teste.com")
          .cpf("333.333.333-33")
          .resumo("kk")
          .urlFoto("http://")
          .build();

      return egresso;

    }
    
    @Test
    public void deveVerificarSalvarDepoimento() throws Exception {
      
      //cenário
      Depoimento entity = this.createCenario();

      //ação
       
      Depoimento salvo = repository.save(entity);  //salva?
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(entity.getTexto(), salvo.getTexto());

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarBuscarDepoimentoPorEgresso() throws Exception {
      
      //cenário
      Depoimento depoimento = this.createCenario();
      Egresso egresso = this.createCenarioEgresso();
      egresso = repositoryEgresso.save(egresso);
      depoimento.setEgresso(egresso);

      //ação
       
      Depoimento salvo = repository.save(depoimento);  //salva?
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(depoimento.getTexto(), salvo.getTexto());
      Assertions.assertEquals(depoimento.getData(), salvo.getData());
      Assertions.assertEquals(depoimento.getEgresso().getId(), salvo.getEgresso().getId());

      // repositoryEgresso.delete(salvo);
    }

    @Test
    public void deveVerificarExisteDepoimentoPorEgresso() throws Exception {
      
      //cenário
      Depoimento depoimento = this.createCenario();
      Egresso egresso = this.createCenarioEgresso();
      egresso = repositoryEgresso.save(egresso);
      depoimento.setEgresso(egresso);
      
      //ação
      repository.save(depoimento);  
      boolean ret = repository.existsByEgresso(egresso);  
      
      //verificação
      Assertions.assertTrue(ret);
      // repositoryEgresso.delete(salvo);
    }

    @Test
    @Transactional
    public void deveVerificarDeletaDepoimentoPorEgresso() throws Exception {
      
      //cenário
      Depoimento depoimento = this.createCenario();
      Egresso egresso = this.createCenarioEgresso();
      egresso = repositoryEgresso.save(egresso);
      depoimento.setEgresso(egresso);
      
      //ação
      repository.save(depoimento);

      repository.deleteByEgresso(egresso);  
      boolean ret = repository.existsByEgresso(egresso);  
      
      //verificação
      Assertions.assertFalse(ret);
    }

}