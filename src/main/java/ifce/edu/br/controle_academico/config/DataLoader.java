package ifce.edu.br.controle_academico.config;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.Role;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadInitial(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setLogin("admin");
                admin.setSenha(encoder.encode("adm123"));
                admin.setRole(Role.ROLE_ADMIN);
                usuarioRepository.save(admin);

                Usuario sec = new Usuario();
                sec.setLogin("secretaria");
                sec.setSenha(encoder.encode("sec123"));
                sec.setRole(Role.ROLE_SECRETARIA);
                usuarioRepository.save(sec);
            }
        };
    }
}