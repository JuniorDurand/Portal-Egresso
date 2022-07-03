package egresso.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class CursoService {

    @Autowired
    CursoRepo repository;

    @Autowired
    CursoEgressoRepo repository_cursoEgresso;

    @Autowired
    EgressoRepo repository_egresso;


    @Transactional
    public Curso salvarCurso(Curso curso) {
        verificarDadosCurso(curso);
        return repository.save(curso);
    }

    public Curso editarCurso(Curso curso){
        varificarIdCurso(curso);
        return repository.save(curso);
    }

    public void removerCurso(Curso curso){
        varificarIdCurso(curso);
        verificarCursoEgresso(curso);
        repository.delete(curso);
    }

    public List<Egresso> buscarEgressoPorCurso(Curso curso){
        List<CursoEgresso> cursos_egressos = repository_cursoEgresso.findByCurso(curso);
        List<Egresso> egressos = new ArrayList();
        for (CursoEgresso cursoEgresso : cursos_egressos) {
            egressos.add(cursoEgresso.getEgresso());
        }
        return egressos;
    }

    public Long qntEgressoByCurso(Curso curso){
        varificarIdCurso(curso);
        return repository_egresso.qntEgressoByCurso(curso);
    }


    private void verificarDadosCurso(Curso curso) {
        if (curso == null)
            throw new RegraNegocioRunTime("Curso inválido");                
        if ((curso.getNome() == null) || (curso.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do curso deve ser informado");    
        if ((curso.getNivel() == null) || (curso.getNivel().equals("")))
            throw new RegraNegocioRunTime("Nivel do curso deve ser informado");          
    }

    private void verificarCursoEgresso(Curso curso) {
        List<CursoEgresso> res = repository_cursoEgresso.findByCurso(curso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("O Curso informado está vinculado a egressos");
    }

    private void varificarIdCurso(Curso curso){
        if ((curso == null) || (curso.getId() == null))
            throw new RegraNegocioRunTime("Curso não identificado");
    }

    public Curso get(Curso curso) {
        this.varificarIdCurso(curso);
        Optional<Curso> ret = repository.findById(curso.getId());

        if (ret.isPresent()){
            return ret.get();
        }
        return null;
    }
    
}
