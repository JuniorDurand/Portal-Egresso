package egresso.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.dto.CargoDTO;
import egresso.demo.service.CargoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cargo")
public class CargoController {
    
    @Autowired
    CargoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CargoDTO dto) {
        try {
            Cargo carg = Cargo.builder()
                                .nome(dto.getNome())
                                .descricao(dto.getDescricao())
                                .ProfEgressos(dto.getProfEgressos())
                                .build();

            Cargo saved = service.save(carg);
            // return ResponseEntity.created(saved);
            ResponseEntity response = new ResponseEntity( saved, HttpStatus.CREATED);
            return response;
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long idCargo, @RequestBody CargoDTO dto) {
        try {
            Cargo cur = service.get(Cargo.builder().id(idCargo).build());

            Cargo carg = Cargo.builder()
                                .id((idCargo))
                                .nome((dto.getNome() == null ? cur.getNome() : dto.getNome()))
                                .nome((dto.getDescricao() == null ? cur.getDescricao() : dto.getDescricao()))
                                .ProfEgressos(cur.getProfEgressos())
                                .build();

            Cargo saved = service.update(carg);
            ResponseEntity response = new ResponseEntity( saved, HttpStatus.CREATED);
            return response;
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity remover(@PathVariable("id") Long idCargo) {
        try {
            Cargo carg = Cargo.builder()
                                .id((idCargo))
                                .build();

            service.remove(carg);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("egresso/{id}")
    public ResponseEntity obterCargoPorEgresso(@PathVariable("id") Long idEgresso) {
        try {
            Egresso egr = Egresso.builder()
                                .id((idEgresso))
                                .build();

            List<Cargo> cargs = service.getCargoByEgresso(egr);
            return ResponseEntity.ok(cargs);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("count/egresso/{id}")
    public ResponseEntity quantidadeDeEgressoPorCargo(@PathVariable("id") Long idCargo) {
        try {
            Cargo carg = Cargo.builder()
                                .id((idCargo))
                                .build();

            Long count = service.countEgressoByCargo(carg);
            return ResponseEntity.ok(count);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
