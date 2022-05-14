package egresso.demo.entity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;

public interface ProfEgressoRepo extends JpaRepository<ProfEgresso, Long> {
    List<ProfEgresso> findByEgresso(Egresso egresso);
    boolean existsByEgresso(Egresso egresso);
    void deleteByEgresso(Egresso egresso);
}
