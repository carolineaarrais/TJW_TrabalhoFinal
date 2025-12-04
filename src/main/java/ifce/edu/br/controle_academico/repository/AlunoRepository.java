package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByMatricula(String matricula);
    Optional<Aluno> findByMatricula(String matricula);
}