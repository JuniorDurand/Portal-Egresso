package egresso.demo.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.FaixaSalario;

public interface FaixaSalarioRepo extends JpaRepository<FaixaSalario, Long> {
    
}
