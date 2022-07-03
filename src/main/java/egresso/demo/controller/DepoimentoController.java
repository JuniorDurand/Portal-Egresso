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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.dto.DepoimentoDTO;
import egresso.demo.service.DepoimentoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/depoimento")
public class DepoimentoController {
    
    @Autowired
    DepoimentoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody DepoimentoDTO dto) {
        try {
            Depoimento dep = Depoimento.builder()
                                .texto(dto.getTexto())
                                .data(dto.getData())
                                .egresso(Egresso.builder().id(dto.getIdEgresso()).build())
                                .build();

            Depoimento saved = service.save(dep);
            ResponseEntity<Object> response = new ResponseEntity<Object>( saved, HttpStatus.CREATED);
            return response;
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Long idDepoimento, @RequestBody DepoimentoDTO dto) {
        try {

            Depoimento cur = service.get(Depoimento.builder().id(idDepoimento).build());



            Depoimento dep = Depoimento.builder()
                                .id(idDepoimento)
                                .texto((dto.getTexto() == null) ? cur.getTexto() : dto.getTexto())
                                .data((dto.getData() == null) ? cur.getData() : dto.getData())
                                .build();

            if(dto.getIdEgresso() != null){
                dep.setEgresso(Egresso.builder().id(dto.getIdEgresso()).build());  
            }else{
                dep.setEgresso(cur.getEgresso());  
            }


            Depoimento saved = service.update(dep);
            return ResponseEntity.ok(saved);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> remover(@PathVariable("id") Long idDepoimento) {
        try {
            Depoimento dep = Depoimento.builder()
                                .id(idDepoimento)
                                .build();

            service.remove(dep);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterDepoimento(@PathVariable("id") Long idDepoimento) {
        try {

            Depoimento dep = service.get(Depoimento.builder().id(idDepoimento).build());
            return ResponseEntity.ok(dep);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping()
    public ResponseEntity<Object> obterOrdenadosPorMaisRecente() {
        try {
            List<Depoimento> deps = service.getDepoimentosOrderByMostRecent();
            return ResponseEntity.ok(deps);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("egresso")
    public ResponseEntity<Object> obterPorEgresso(@RequestParam("id") Long idEgresso) {
        try {
            Egresso egr = Egresso.builder().id(idEgresso).build();

            List<Depoimento> deps = service.getDepoimentosByEgresso(egr);
            return ResponseEntity.ok(deps);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
