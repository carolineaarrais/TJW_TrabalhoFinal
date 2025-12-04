package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
    boolean existsByLogin(String login);
}