package egresso.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class FaixaSalarioService {

    @Autowired
    FaixaSalarioRepo repository;

    @Autowired
    EgressoRepo repository_egresso;

    @Transactional
    public FaixaSalario salvarFaixaSalario(FaixaSalario faixa_salario) {
        verificarDadosFaixaSalario(faixa_salario);
        return repository.save(faixa_salario);
    }

    public FaixaSalario editarFaixaSalario(FaixaSalario faixa_salario){
        varificarIdFaixaSalario(faixa_salario);
        return repository.save(faixa_salario);
    }

    public void removerFaixaSalario(FaixaSalario faixa_salario){
        varificarIdFaixaSalario(faixa_salario);
        verificarDadosFaixaSalario(faixa_salario);
        repository.delete(faixa_salario);
    }

    public FaixaSalario get(FaixaSalario faixa_salario){
        varificarIdFaixaSalario(faixa_salario);
        Optional<FaixaSalario> ret = repository.findById(faixa_salario.getId());

        if (ret.isPresent()){
            return ret.get();
        }
        return null;
    }

    public Long qntEgressoByFaixaSalario(FaixaSalario faixa_salario){
        varificarIdFaixaSalario(faixa_salario);
        return repository_egresso.qntEgressoByFaixaSalario(faixa_salario);
    }

    private void varificarIdFaixaSalario(FaixaSalario faixa_salario){
        if ((faixa_salario == null) || (faixa_salario.getId() == null))
            throw new RegraNegocioRunTime("Faixa de Salário não identificado");
    }

    private void verificarDadosFaixaSalario(FaixaSalario faixa_salario) {
        if (faixa_salario == null)
            throw new RegraNegocioRunTime("Curso inválido");                
        if ((faixa_salario.getDescricao() == null) || (faixa_salario.getDescricao().equals("")))
            throw new RegraNegocioRunTime("Descrição da faixa de salário não definida");           
    }
    
}
