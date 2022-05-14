package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Cargo;

public interface CargoRepo extends JpaRepository<Cargo, Long> {
    
    List<Cargo> findByNome(String nome);
    boolean existsByNome(String nome);
}
