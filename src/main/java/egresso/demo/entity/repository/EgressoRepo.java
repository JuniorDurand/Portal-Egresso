package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Egresso;

public interface EgressoRepo extends JpaRepository<Egresso, Long> {
    
    List<Egresso> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    List<Egresso> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
}
