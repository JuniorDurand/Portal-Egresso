package egresso.demo.entity.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso, CursoEgressoId> {
    
    List<CursoEgresso> findByEgresso(Egresso egresso);
    List<CursoEgresso> findByCurso(Curso curso);

    boolean existsByEgresso(Egresso egresso);
    boolean existsByCurso(Curso curso);

    void deleteByEgresso(Egresso egresso);
}
