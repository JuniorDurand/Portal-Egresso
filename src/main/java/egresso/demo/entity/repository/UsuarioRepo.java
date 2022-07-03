package egresso.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Usuario;

public interface UsuarioRepo 
    extends JpaRepository<Usuario, Long>{
    
        Optional<Usuario> findByEmail(String email);
        boolean existsByEmail(String email);

        @Query("select u from Usuario u where u.nome=:nomeUsuario")
        List<Usuario> obterUsuarioPorNome(
            @Param("nomeUsuario") String nomeUsuario);
        
}
