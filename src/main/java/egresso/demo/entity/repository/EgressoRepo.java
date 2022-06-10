package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;

public interface EgressoRepo extends JpaRepository<Egresso, Long> {
    
    List<Egresso> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    List<Egresso> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);

    @Query("SELECT COUNT(*) FROM Egresso e"
    + " JOIN e.ProfEgressos pe"
    + " JOIN pe.cargo c"
    + " WHERE c = :cargo")
    Long countEgressoByCargo( @Param("cargo") Cargo cargo);
}
