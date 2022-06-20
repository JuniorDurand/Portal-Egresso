package egresso.demo.service;


import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.CargoServiceRunTime;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CargoServiceTest {

    @Autowired
    CargoService service;

    @Autowired
    CargoRepo repository;
    @Autowired
    EgressoRepo egressoRepo;
    @Autowired
    ProfEgressoRepo profEgressoRepo;

    private Cargo generateCargo(){
        return Cargo.builder()
            .nome("Cargo teste")
            .descricao("Descricao de cargo teste")
            .build();
    }

    private Egresso generateEgresso(){
        return Egresso.builder()
            .nome("Egresso Teste")
            .email("teste@teste.com")
            .cpf("333.333.333-33")
            .resumo("teste")
            .urlFoto("http://")
            .build();
    }

    private ProfEgresso generateProfEgresso(Cargo cargo, Egresso egresso){
        return ProfEgresso.builder()
            .empresa("empresa teste")
            .descricao("descricao teste")
            .dataRegistro(LocalDate.of(2020, 1, 8))
            .egresso(egresso)
            .cargo(cargo)
            .build();
    }
    
    @Test
    void deveVerificarSalvarCargo(){
        
        //cenário
        Cargo cargo = this.generateCargo();

        //ação
        Cargo ret = service.save(cargo);

        //verificação
        Assertions.assertNotNull(ret);
        Assertions.assertNotNull(ret.getId());
    }

    @Test
    void deveVerificarSalvarCargoNulo(){
        
        //cenário
        Cargo cargo = null;

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.save(cargo), "Um cargo valido deve ser informado");
    }

    @Test
    void deveVerificarSalvarCargoSemNome(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo.setNome(null);

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.save(cargo), "Um nome deve ser informado no cargo");
    }

    @Test
    void deveVerificarSalvarCargoSemDescricao(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo.setNome(null);

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.save(cargo), "Uma descricao deve ser informada no cargo");
    }

    @Test
    void deveVerificarAtualizarCargo(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = service.save(cargo);
        
        //ação
        cargo.setNome("novo nome cargo");
        Cargo ret = service.update(cargo);
        
        //verificação
        Assertions.assertNotNull(ret);
        Assertions.assertEquals("novo nome cargo", ret.getNome());

    }

    @Test
    void deveVerificarAtualizarCargoSemId(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        
        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.update(cargo), "Um cargo valido deve ser informado");
    }

    @Test
    void deveVerificarAtualizarCargoNulo(){
        
        //cenário
        Cargo cargo = null;

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.update(cargo), "Um cargo valido deve ser informado");
    }

    @Test
    void deveVerificarAtualizarCargoSemNome(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo.setNome(null);

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.update(cargo), "Um nome deve ser informado no cargo");
    }

    @Test
    void deveVerificarAtualizarCargoSemDescricao(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo.setNome(null);

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.update(cargo), "Uma descricao deve ser informada no cargo");
    }

    @Test
    void deveVerificarDeletarCargo(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);

        //ação
        service.remove(cargo);
        
        //verificação
        boolean ret = repository.existsById(cargo.getId());
        Assertions.assertFalse(ret);
    }

    @Test
    void deveVerificarDeletarCargoSemId(){
        
        //cenário
        Cargo cargo = this.generateCargo();

        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.remove(cargo), "Uma descricao deve ser informada no cargo");
    }

    @Test
    void deveVerificarPegarCargoPorEgresso(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);


        
        //ação
        List<Cargo> retList = service.getCargoByEgresso(egresso);
        Cargo ret = retList.get(0);
        
        //verificação
        Assertions.assertEquals(cargo.getId(), ret.getId());
    }

    @Test
    void deveVerificarPegarCargoPorEgressoSemId(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);
        egresso.setId(null);
        final Egresso egressoFinal = egresso;


        
        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.getCargoByEgresso(egressoFinal), "Um egresso valido deve ser informado");
    }

    @Test
    void deveVerificarPegarCargoPorEgressoNulo(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);
        final Egresso egressoFinal = null;


        
        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.getCargoByEgresso(egressoFinal), "Um egresso valido deve ser informado");
    }

    @Test
    void deveVerificarContarEgressoPorCargo(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);


        
        //ação
        Long ret = service.countEgressoByCargo(cargo);
        
        
        //verificação
        Assertions.assertEquals(1, ret);
    }

    @Test
    void deveVerificarContarEgressoPorCargoNulo(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);
        final Cargo cargoFinal = null;


        
        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.countEgressoByCargo(cargoFinal), "Um cargo valido deve ser informado");
    }

    @Test
    void deveVerificarContarEgressoPorCargoSemId(){
        
        //cenário
        Cargo cargo = this.generateCargo();
        cargo = repository.save(cargo);
        Egresso egresso = this.generateEgresso();
        egresso = egressoRepo.save(egresso);
        ProfEgresso profEgresso = this.generateProfEgresso(cargo, egresso);
        profEgresso = profEgressoRepo.save(profEgresso);

        cargo.setId(null);
        final Cargo cargoFinal = cargo;


        
        //ação e verificação
        Assertions.assertThrows(CargoServiceRunTime.class, () -> service.countEgressoByCargo(cargoFinal), "Um cargo valido deve ser informado");
    }


}
