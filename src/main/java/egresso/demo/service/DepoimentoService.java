package egresso.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.service.exceptions.DepoimentoServiceRunTime;

@Service
public class DepoimentoService {

    @Autowired
    DepoimentoRepo repository;


    public Depoimento save(Depoimento depoimento){
        this.checkDepoimento(depoimento);
        return repository.save(depoimento);
    }

    public Depoimento update(Depoimento depoimento){
        this.checkDepoimentoWithId(depoimento);
        return repository.save(depoimento);
    }

    public void remove(Depoimento depoimento){
        this.checkDepoimentoWithId(depoimento);
        repository.delete(depoimento);
    }
    public List<Depoimento> getDepoimentosOrderByMostRecent(){
        return repository.findAll(Sort.by("data"));
    }
    public List<Depoimento> getDepoimentosByEgresso(Egresso egresso){
        this.checkEgresso(egresso);
        return repository.findByEgresso(egresso);
    }

    private void checkDepoimento(Depoimento depoimento){
        if(depoimento == null){
            throw new DepoimentoServiceRunTime("Um depoimento valido deve ser informado");
        }else if(depoimento.getTexto() == null || depoimento.getTexto() == ""){
            throw new DepoimentoServiceRunTime("Um texto deve ser informado no depoimento"); 
        }else if(depoimento.getData() == null){
            throw new DepoimentoServiceRunTime("Uma data valida deve ser informada no depoimento");
        }else if(depoimento.getEgresso() == null){
            throw new DepoimentoServiceRunTime("Um egresso deve ser informado no depoimento");
        }else if(depoimento.getEgresso().getId() == null){
            throw new DepoimentoServiceRunTime("Um egresso valido deve ser informado no depoimento");
        } 
    }

    private void checkEgresso(Egresso egresso){
        if(egresso == null){
            throw new DepoimentoServiceRunTime("Um egresso valido deve ser informado");
        }else if(egresso.getId() == null){
            throw new DepoimentoServiceRunTime("Um egresso valido deve ser informado"); 
        }
    }

    private void checkDepoimentoWithId(Depoimento depoimento){
        
        this.checkDepoimento(depoimento);
        if(depoimento.getId() == null){
            throw new DepoimentoServiceRunTime("Um depoimento valido deve ser informado");
        }
    }
}
