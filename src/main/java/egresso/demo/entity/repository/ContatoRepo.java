package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Contato;

public interface ContatoRepo extends JpaRepository<Contato, Long> {
    
    List<Contato> findByNome(String nome);
    boolean existsByNome(String nome);
}
