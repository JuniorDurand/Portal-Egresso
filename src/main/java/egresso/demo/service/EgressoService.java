package egresso.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class EgressoService {
    @Autowired
    EgressoRepo repository;

    @Autowired
    CursoRepo repository_curso;

    @Autowired
    CursoEgressoRepo repository_cursoEgresso;

    @Autowired
    CargoRepo repository_cargo;

    @Autowired
    FaixaSalarioRepo repository_faixa_salario;

    @Autowired
    ProfEgressoRepo repository_profEgresso;

    @Autowired
    DepoimentoRepo repository_depoimento;

    @Transactional
    public Egresso salvarCadastro(Egresso egresso, List<CursoEgresso> cursos, List<ProfEgresso> proffissoes) {
        verificarDadosEgressoNovo(egresso);
        Egresso egresso_salvo = repository.save(egresso);
        if (egresso_salvo == null) throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados do egresso");

        for (CursoEgresso curso : cursos) {
            curso.setEgresso(egresso_salvo);
            verificarDadosCursoEgresso(curso, egresso_salvo);
            if (repository_cursoEgresso.save(curso) == null) 
                throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados do curso");
        }

        for (ProfEgresso profEgresso : proffissoes) {
            profEgresso.setEgresso(egresso_salvo);
            verificarDadosProfEgresso(profEgresso);
            if (repository_profEgresso.save(profEgresso) == null) 
                throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados da profissão");
        }
        
        return egresso_salvo;
    }

    public Egresso editarCadastro(Egresso egresso, List<CursoEgresso> cursos, List<ProfEgresso> proffissoes){
        if ((egresso == null) || (egresso.getId() == null))
            throw new RegraNegocioRunTime("Egresso não identificado");
        verificarDadosEgresso(egresso);
        Egresso egresso_salvo = repository.save(egresso);
        if (egresso_salvo == null) throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados do egresso");

        removerCursoEgresso(egresso_salvo);
        for (CursoEgresso curso : cursos) {
            curso.setEgresso(egresso_salvo);
            verificarDadosCursoEgresso(curso, egresso_salvo);
            if (repository_cursoEgresso.save(curso) == null) 
                throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados do curso");
        }

        removerProfEgresso(egresso_salvo);
        for (ProfEgresso profEgresso : proffissoes) {
            profEgresso.setEgresso(egresso_salvo);
            verificarDadosProfEgresso(profEgresso);
            if (repository_profEgresso.save(profEgresso) == null) 
                throw new RegraNegocioRunTime("Ocorreu um problema inesperado ao salvar os dados da profissão");
        }

        return egresso_salvo;
    }

    public void removerEgresso(Egresso egresso) {  
        if ((egresso == null) || (egresso.getId() == null))
            throw new RegraNegocioRunTime("Egresso sem id");      
        verificarDepoimento(egresso);
        verificarProfEgresso(egresso);
        verificarCursoEgresso(egresso);
        repository.delete(egresso);
    }

    public void removerCursoEgresso(Egresso egresso) {        
        List<CursoEgresso> cursos = repository_cursoEgresso.findByEgresso(egresso);
        for (CursoEgresso cursoEgresso : cursos) {
            repository_cursoEgresso.delete(cursoEgresso);
        }
        
    }
    public void removerProfEgresso(Egresso egresso) {        
        List<ProfEgresso> profissoes = repository_profEgresso.findByEgresso(egresso);
        for (ProfEgresso profEgresso : profissoes) {
            repository_profEgresso.delete(profEgresso);
        }
    }
    
    private void verificarDepoimento(Egresso egresso) {
        List<Depoimento> res = repository_depoimento.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui depoimento");
    }
    private void verificarProfEgresso(Egresso egresso) {
        List<ProfEgresso> res = repository_profEgresso.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui uma profissão");
    }
    private void verificarCursoEgresso(Egresso egresso) {
        List<CursoEgresso> res = repository_cursoEgresso.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado está vinculado a um curso");
    }

    public Egresso buscarEgresso(Egresso egresso){
        return repository.getById(egresso.getId());
    }
    
    
    private void verificarDadosEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRunTime("Um egresso válido deve ser informado");                
        if ((egresso.getNome() == null) || (egresso.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do egresso deve ser informado");    
        if ((egresso.getEmail() == null) || (egresso.getEmail().equals("")))
            throw new RegraNegocioRunTime("Email deve ser informado");          
        if ((egresso.getCpf() == null) || (egresso.getCpf().equals("")))
            throw new RegraNegocioRunTime("CPF deve ser informado");
        if ((egresso.getResumo() == null) || (egresso.getResumo().equals("")))
            throw new RegraNegocioRunTime("Resumo deve ser informado");
        if ((egresso.getUrlFoto() == null) || (egresso.getUrlFoto().equals("")))
            throw new RegraNegocioRunTime("Uma foto deve ser inserida");
    }
    private void verificarDadosEgressoNovo(Egresso egresso) {
        verificarDadosEgresso(egresso);
        boolean email = repository.existsByEmail(egresso.getEmail());
        if (email) throw new RegraNegocioRunTime("Email informado já existe");  
        boolean cpf = repository.existsByCpf(egresso.getCpf());
        if (cpf) throw new RegraNegocioRunTime("CPF informado já existe");
    }

    private void verificarDadosCursoEgresso(CursoEgresso curso, Egresso egresso) {
        if (egresso == null || (egresso.getId() == null))
            throw new RegraNegocioRunTime("Egresso invalido");  
        if (curso == null || (curso.getId() == null))
            throw new RegraNegocioRunTime("Curso invalido");
        if ((curso.getDataInicio() == null) || (curso.getDataInicio().equals("")))
            throw new RegraNegocioRunTime("Data de inicio do curso é inválida"); 
        if ((curso.getDataConclusao() == null) || (curso.getDataConclusao().equals("")))
            throw new RegraNegocioRunTime("Data de fim do curso é inválida"); 
    }

    private void verificarDadosProfEgresso(ProfEgresso profEgresso) {
        if ((profEgresso.getEgresso() == null))
            throw new RegraNegocioRunTime("Egresso inválido");
        if ((profEgresso.getCargo() == null))
            throw new RegraNegocioRunTime("Cargo inválido");
        if ((profEgresso.getFaixaSalario() == null))
            throw new RegraNegocioRunTime("Faixa Salario inválido");
    }
}