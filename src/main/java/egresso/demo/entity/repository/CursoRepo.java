package egresso.demo.entity.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Curso;

public interface CursoRepo extends JpaRepository<Curso, Long> {
    
    List<Curso> findByNome(String nome);
    boolean existsByNome(String nome);
    void deleteByNome(String nome);
}
