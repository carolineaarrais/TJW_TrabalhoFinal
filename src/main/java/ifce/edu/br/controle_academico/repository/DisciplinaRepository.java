package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByCodigo(String codigo);
}