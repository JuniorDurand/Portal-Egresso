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

import egresso.demo.entity.Curso;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.dto.CursoDTO;
import egresso.demo.service.CursoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/curso")
public class CursoController {
    
    @Autowired
    CursoService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CursoDTO dto) {
        try {
            Curso curso = Curso.builder()
                                .nome(dto.getNome())
                                .nivel(dto.getNivel())
                                .build();

            Curso saved = service.salvarCurso(curso);
            ResponseEntity<Object> response = new ResponseEntity<Object>( saved, HttpStatus.CREATED);
            return response;
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Long idCurso, @RequestBody CursoDTO dto) {
        try {

            Curso cur = service.get(Curso.builder().id(idCurso).build());

            Curso curso = Curso.builder()
                                .id(idCurso)
                                .nome((dto.getNome() == null) ? cur.getNome() : dto.getNome())
                                .nivel((dto.getNivel() == null) ? cur.getNivel() : dto.getNivel())
                                .cursoEgresso(cur.getCursoEgresso())
                                .build();



            Curso saved = service.editarCurso(curso);
            return ResponseEntity.ok(saved);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> remover(@PathVariable("id") Long idCurso) {
        try {
            Curso curso = Curso.builder()
                                .id(idCurso)
                                .build();

            service.removerCurso(curso);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("{id}")
    public ResponseEntity<Object> ObterCurso(@PathVariable("id") Long idCurso) {
        try {
            Curso curso = service.get(Curso.builder().id(idCurso).build());

            return ResponseEntity.ok(curso);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/egressos/{id}")
    public ResponseEntity<Object> ObterEgressosPorCurso(@PathVariable("id") Long idCurso) {
        try {
            Curso curso = Curso.builder()
                                .id(idCurso)
                                .build();

            List<Egresso> egrs = service.buscarEgressoPorCurso(curso);
            return ResponseEntity.ok(egrs);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count/egresso/{id}")
    public ResponseEntity<Object> ObterQuantidadeDeEgressosPorCurso(@PathVariable("id") Long idCurso) {
        try {
            Curso curso = Curso.builder()
                                .id(idCurso)
                                .build();

            Long count = service.qntEgressoByCurso(curso);
            return ResponseEntity.ok(count);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
