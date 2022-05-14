package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;

public interface DepoimentoRepo extends JpaRepository<Depoimento, Long> {
    
    List<Depoimento> findByEgresso(Egresso egresso);
    boolean existsByEgresso(Egresso egresso);
    void deleteByEgresso(Egresso egresso);
}
