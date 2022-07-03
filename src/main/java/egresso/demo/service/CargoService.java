package egresso.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.service.exceptions.CargoServiceRunTime;

@Service
public class CargoService {

    @Autowired
    CargoRepo repository;
    @Autowired
    EgressoRepo repositoryEgresso;

    public Cargo save(Cargo cargo){
        this.checkCargo(cargo);
        return repository.save(cargo);
    }

    public Cargo update(Cargo cargo){
        this.checkCargoWithId(cargo);
        return repository.save(cargo);
    }
    
    public void remove(Cargo cargo){
        this.checkCargoWithId(cargo);
        repository.delete(cargo);
    }

    public Cargo get(Cargo cargo){
        this.checkCargoOnlyId(cargo);
        Optional<Cargo> ret = repository.findById(cargo.getId());

        if (ret.isPresent()){
            return ret.get();
        }
        return null;
    }
    
    public List<Cargo> getCargoByEgresso(Egresso egresso){
        this.checkEgresso(egresso);
        return repository.findCargoByEgresso(egresso);
    }

    public Long countEgressoByCargo(Cargo cargo){
        this.checkCargoWithId(cargo);
        return repositoryEgresso.countEgressoByCargo(cargo);
    }

    private void checkCargo(Cargo cargo){
        if(cargo == null){
            throw new CargoServiceRunTime("Um cargo valido deve ser informado");
        }else if(cargo.getNome() == null || cargo.getNome() == ""){
            throw new CargoServiceRunTime("Um nome deve ser informado no cargo"); 
        }else if(cargo.getDescricao() == null || cargo.getDescricao() == ""){
            throw new CargoServiceRunTime("Uma descricao deve ser informada no cargo"); 
        }
    }
    
    private void checkCargoOnlyId(Cargo cargo){
        if(cargo.getId() == null){
            throw new CargoServiceRunTime("Um cargo valido deve ser informado");
        }else if(!repository.existsById(cargo.getId())){
            throw new CargoServiceRunTime("Um cargo valido deve ser informado");
        }
    }

    private void checkCargoWithId(Cargo cargo){
        this.checkCargoOnlyId(cargo);
        this.checkCargo(cargo);
    }


    private void checkEgresso(Egresso egresso){
        if(egresso == null){
            throw new CargoServiceRunTime("Um egresso valido deve ser informado");
        }else if(egresso.getId() == null){
            throw new CargoServiceRunTime("Um egresso valido deve ser informado"); 
        }
    }
}
