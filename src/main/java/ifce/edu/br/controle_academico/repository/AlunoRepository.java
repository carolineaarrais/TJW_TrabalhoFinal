package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    boolean existsByMatricula(String matricula);
}