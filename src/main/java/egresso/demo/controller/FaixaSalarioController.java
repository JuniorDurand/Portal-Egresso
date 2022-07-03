package egresso.demo.controller;

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

import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.dto.FaixaSalarioDTO;
import egresso.demo.service.FaixaSalarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/faixa-salario")
public class FaixaSalarioController {
    
    @Autowired
    FaixaSalarioService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody FaixaSalarioDTO dto) {
        try {
            FaixaSalario faixa = FaixaSalario.builder()
                                .descricao(dto.getDescricao())
                                .build();

            FaixaSalario saved = service.salvarFaixaSalario(faixa);
            ResponseEntity<Object> response = new ResponseEntity<Object>( saved, HttpStatus.CREATED);
            return response;
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Long idFaixaSalario, @RequestBody FaixaSalarioDTO dto) {
        try {

            FaixaSalario cur = service.get(FaixaSalario.builder().id(idFaixaSalario).build());

            FaixaSalario faixa = FaixaSalario.builder()
                                .id(idFaixaSalario)
                                .descricao((dto.getDescricao() == null) ? cur.getDescricao() : dto.getDescricao())
                                .ProfEgressos(cur.getProfEgressos())
                                .build();



            FaixaSalario saved = service.editarFaixaSalario(faixa);
            return ResponseEntity.ok(saved);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> remover(@PathVariable("id") Long idFaixaSalario) {
        try {
            FaixaSalario faixa = FaixaSalario.builder()
                                .id(idFaixaSalario)
                                .build();

            service.removerFaixaSalario(faixa);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("{id}")
    public ResponseEntity<Object> ObterCurso(@PathVariable("id") Long idFaixaSalario) {
        try {
            FaixaSalario faixa = service.get(FaixaSalario.builder().id(idFaixaSalario).build());

            return ResponseEntity.ok(faixa);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count/egresso/{id}")
    public ResponseEntity<Object> ObterQuantidadeDeEgressosPorFaixaSalario(@PathVariable("id") Long idFaixaSalario) {
        try {
            Long count = service.qntEgressoByFaixaSalario(FaixaSalario.builder().id(idFaixaSalario).build());

            return ResponseEntity.ok(count);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
