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

import egresso.demo.entity.repository.ContatoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class ContatoRepoTest {

    @Autowired
    ContatoRepo repository;

    private Contato createCenario(){
      Contato contato = Contato.builder()
          .nome("Teste")
          .urlLogo("urlLogoTeste")
          .build();

      return contato;
    }
    
    @Test
    public void deveVerificarSalvarContato() throws Exception {
      
      //cenário
      Contato contato = this.createCenario();

      //ação
       
      Contato salvo = repository.save(contato);  //salva?
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(contato.getNome(), salvo.getNome());
      Assertions.assertEquals(contato.getUrlLogo(), salvo.getUrlLogo());

      repository.delete(salvo);
    }


    @Test
    public void deveVerificarBuscarContatoPorNome() throws Exception {
      
      //cenário
      Contato contato = this.createCenario();

      //ação
       
      Contato salvo = repository.save(contato);  //salva?
      List <Contato>  retList = repository.findByNome(contato.getNome());
      
      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Contato ret = retList.get(0);

      Assertions.assertEquals(contato.getNome(), ret.getNome());
      Assertions.assertEquals(contato.getUrlLogo(), ret.getUrlLogo());

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarExisteContatoPorNome() throws Exception {
      
      //cenário
      Contato contato = this.createCenario();

      //ação
       
      Contato salvo = repository.save(contato);  //salva?
      boolean  ret = repository.existsByNome(contato.getNome());
      
      //verificação
      Assertions.assertTrue(ret);

      repository.delete(salvo);
    }
}