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

import egresso.demo.entity.repository.CargoRepo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional // o Transactional não persiste dados em teste, faz rollback após a conclusão deles
public class CargoRepoTest {

    @Autowired
    CargoRepo repository;

    private Cargo createCenario(){
      Cargo cargo = Cargo.builder()
          .nome("Teste")
          .descricao("Descricao teste")
          .build();

      return cargo;
    }
    
    @Test
    public void deveVerificarSalvarCargo() throws Exception {
      
      //cenário
      Cargo cargo = this.createCenario();

      //ação
      //  
      Cargo salvo = repository.save(cargo); 
      
      //verificação
      Assertions.assertNotNull(salvo);
      Assertions.assertEquals(cargo.getNome(), salvo.getNome());
      Assertions.assertEquals(cargo.getDescricao(), salvo.getDescricao());

      // repository.delete(salvo);
    }

    @Test
    public void deveVerificarBuscarCargoPorNome() throws Exception {
      
      //cenário
      Cargo cargo = this.createCenario();

      //ação
      //  
      Cargo salvo = repository.save(cargo); 
      List <Cargo>  retList = repository.findByNome(cargo.getNome());
      
      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Cargo ret = retList.get(0);

      Assertions.assertEquals(cargo.getNome(), ret.getNome());
      Assertions.assertEquals(cargo.getDescricao(), ret.getDescricao());

      repository.delete(salvo);
    }

    @Test
    public void deveVerificarExisteCargoPorNome() throws Exception {
      
      //cenário
      Cargo cargo = this.createCenario();

      //ação
      //  
      Cargo salvo = repository.save(cargo); 
      boolean ret = repository.existsByNome(cargo.getNome());
      
      //verificação
      Assertions.assertTrue(ret);

      repository.delete(salvo);
    }

}