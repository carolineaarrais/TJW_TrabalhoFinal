package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Matricula;
import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoAndDisciplinaAndSituacao(Aluno aluno, Disciplina disciplina, SituacaoMatricula situacao);
}