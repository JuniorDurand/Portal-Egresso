package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;

public interface CargoRepo extends JpaRepository<Cargo, Long> {
    
    List<Cargo> findByNome(String nome);
    boolean existsByNome(String nome);

    @Query("SELECT c FROM Cargo c"
            + " JOIN c.ProfEgressos pe"
            + " JOIN pe.egresso e"
            + " WHERE e = :egresso")
    List<Cargo> findCargoByEgresso( @Param("egresso") Egresso egresso);
}
