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
import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.DepoimentoServiceRunTime;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DepoimentoServiceTest {

    @Autowired
    DepoimentoService service;

    @Autowired
    DepoimentoRepo repository;
    @Autowired
    EgressoRepo egressoRepo;
    @Autowired
    ProfEgressoRepo profEgressoRepo;

    private Depoimento generateDepoimento(Egresso egresso){
        return Depoimento.builder()
                            .texto("texto")
                            .data(LocalDate.of(2020, 1, 8))
                            .egresso(egresso)
                            .build();
    }

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
    void deveVerificarSalvarDepoimento(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);

        //ação
        Depoimento ret = service.save(depoimento);

        //verificação
        Assertions.assertNotNull(ret);
        Assertions.assertNotNull(ret.getId());
    }

    @Test
    void deveVerificarSalvarDepoimentoNulo(){
        
        //cenário
        Depoimento depoimento = null;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.save(depoimento), "Um depoimento valido deve ser informado");
    }

    @Test
    void deveVerificarSalvarDepoimentoSemTexto(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento.setTexto(null);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.save(depoimento), "Um texto deve ser informado no depoimento");
    }

    @Test
    void deveVerificarSalvarDepoimentoSemData(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento.setData(null);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.save(depoimento), "Uma data valida deve ser informada no depoimento");
    }

    @Test
    void deveVerificarSalvarDepoimentoSemEgresso(){
        
        //cenário
        Depoimento depoimento = this.generateDepoimento(null);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.save(depoimento), "Um egresso deve ser informado no depoimento");
    }

    @Test
    void deveVerificarSalvarDepoimentoSemEgressoValido(){
        
        //cenário
        Egresso egresso = generateEgresso();
        Depoimento depoimento = this.generateDepoimento(egresso);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.save(depoimento), "Um egresso valido deve ser informado no depoimento");
    }

    @Test
    void deveVerificarAtualizarDepoimento(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento = service.save(depoimento);
        depoimento.setTexto("Novo texto");

        //ação
        Depoimento ret = service.update(depoimento);

        //verificação
        Assertions.assertNotNull(ret);
        Assertions.assertEquals("Novo texto", ret.getTexto());
    }

    @Test
    void deveVerificarAtualizarDepoimentoNulo(){
        
        //cenário
        Depoimento depoimento = null;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimento), "Um depoimento valido deve ser informado");
    }

    @Test
    void deveVerificarAtualizarDepoimentoSemId(){
        
        //cenário
        
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimento), "Um depoimento valido deve ser informado");
    }

    @Test
    void deveVerificarAtualizarDepoimentoSemTexto(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento = service.save(depoimento);
        depoimento.setTexto(null);
        final Depoimento depoimentoFinal = depoimento;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimentoFinal), "Um texto deve ser informado no depoimento");
    }

    @Test
    void deveVerificarAtualizarDepoimentoSemData(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento = service.save(depoimento);
        depoimento.setData(null);
        final Depoimento depoimentoFinal = depoimento;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimentoFinal), "Uma data valida deve ser informada no depoimento");
    }

    @Test
    void deveVerificarAtualizarDepoimentoSemEgresso(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento = service.save(depoimento);
        depoimento.setEgresso(null);
        final Depoimento depoimentoFinal = depoimento;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimentoFinal), "Um egresso deve ser informado no depoimento");
    }

    @Test
    void deveVerificarAtualizarDepoimentoSemEgressoValido(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        depoimento = service.save(depoimento);
        depoimento.getEgresso().setId(null);
        final Depoimento depoimentoFinal = depoimento;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.update(depoimentoFinal), "Um egresso valido deve ser informado no depoimento");
    }


    @Test
    void deveVerificarRemoverDepoimento(){
        
        //cenário
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);
        Depoimento depoimentoSalvo = service.save(depoimento);

        //ação
        service.remove(depoimentoSalvo);
        boolean ret = repository.existsById(depoimentoSalvo.getId());

        //verificação
        Assertions.assertFalse(ret);
    }

    @Test
    void deveVerificarRemoverDepoimentoNulo(){
        
        //cenário
        Depoimento depoimento = null;

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.remove(depoimento), "Um depoimento valido deve ser informado");
    }

    @Test
    void deveVerificarRemoverDepoimentoSemId(){
        
        //cenário
        
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);
        Depoimento depoimento = this.generateDepoimento(egresso);

        //ação e verificação
        Assertions.assertThrows(DepoimentoServiceRunTime.class, () -> service.remove(depoimento), "Um depoimento valido deve ser informado");
    }

    @Test
    void deveVerificarObterDepoimentoOrdenadoPorMaisRecente(){
        
        //cenário
        
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);

        repository.deleteAll();
        
        Depoimento depoimento1 = this.generateDepoimento(egresso);
        depoimento1.setData(LocalDate.of(2020, 1, 8));
        repository.save(depoimento1);

        Depoimento depoimento2 = this.generateDepoimento(egresso);
        depoimento2.setData(LocalDate.of(2020, 1, 10));
        repository.save(depoimento2);

        Depoimento depoimento3 = this.generateDepoimento(egresso);
        depoimento3.setData(LocalDate.of(2020, 1, 6));
        repository.save(depoimento3);

        //ação
        List<Depoimento> depoimentos = service.getDepoimentosOrderByMostRecent();
        
        //verificação
        Assertions.assertEquals(depoimento1.getId(), depoimentos.get(1).getId());
        Assertions.assertEquals(depoimento2.getId(), depoimentos.get(2).getId());
        Assertions.assertEquals(depoimento3.getId(), depoimentos.get(0).getId());
    }

    @Test
    void getDepoimentosByEgresso(){
        
        //cenário
        
        Egresso egresso = generateEgresso();
        egresso = egressoRepo.save(egresso);

        repository.deleteAll();
        
        Depoimento depoimento1 = this.generateDepoimento(egresso);
        repository.save(depoimento1);

        Depoimento depoimento2 = this.generateDepoimento(egresso);
        repository.save(depoimento2);

        Depoimento depoimento3 = this.generateDepoimento(egresso);
        repository.save(depoimento3);

        //ação
        List<Depoimento> depoimentos = service.getDepoimentosByEgresso(egresso);
        
        //verificação
        Assertions.assertEquals(3, depoimentos.size());
        Assertions.assertTrue(depoimentos.contains(depoimento1));
        Assertions.assertTrue(depoimentos.contains(depoimento2));
        Assertions.assertTrue(depoimentos.contains(depoimento3));
    }
}
