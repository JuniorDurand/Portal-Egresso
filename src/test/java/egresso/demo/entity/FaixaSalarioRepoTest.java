package egresso.demo.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import egresso.demo.entity.repository.FaixaSalarioRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class FaixaSalarioRepoTest {

    @Autowired
    FaixaSalarioRepo repository;

    private FaixaSalario createCenario(){
      FaixaSalario faixaSalario = FaixaSalario.builder()
          .descricao("Descricao teste")
          .build();

      return faixaSalario;
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