package egresso.demo.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Usuario;
import egresso.demo.entity.repository.UsuarioRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepo repo;

    public Usuario salvar(Usuario usuario){
        verificarUsuario(usuario);
        return repo.save(usuario);
    }

    public Boolean efetuarLogin(String email, String senha){
        Optional<Usuario> usr = repo.findByEmail(email);

        if(!usr.isPresent())
            throw new RegraNegocioRunTime("Erro: Email não cadastrado");
        if(!usr.get().getSenha().equals(senha))
            throw new RegraNegocioRunTime("Erro: Email não cadastrado");

        return true;
    }

    private void verificarUsuario(Usuario usuario) {
        if (usuario == null)
            throw new RegraNegocioRunTime("Usuario inexistente");                
        if ((usuario.getNome() == null) || (usuario.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do usuario deve ser informado");    
        if ((usuario.getEmail() == null) || (usuario.getEmail().equals("")))
            throw new RegraNegocioRunTime("Email usuario ser informado");          
        if ((usuario.getSenha() == null) || (usuario.getSenha().equals("")))
            throw new RegraNegocioRunTime("senha deve ser informado");
    }
    
}
