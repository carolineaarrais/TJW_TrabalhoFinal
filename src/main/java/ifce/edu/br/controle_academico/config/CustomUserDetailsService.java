package ifce.edu.br.controle_academico.config;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByLogin(username);
        if (u == null) throw new UsernameNotFoundException("Usuário não encontrado");
        return User.builder()
                .username(u.getLogin())
                .password(u.getSenha())
                .roles(u.getRole().name().replace("ROLE_", ""))
                .build();
    }
}