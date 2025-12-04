package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Matricula;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import ifce.edu.br.controle_academico.repository.MatriculaRepository;
import ifce.edu.br.controle_academico.exception.MatriculaDuplicadaException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    private final MatriculaRepository repo;

    public MatriculaService(MatriculaRepository repo) { this.repo = repo; }

    public List<Matricula> listarTodas() { return repo.findAll(); }

    public Optional<Matricula> buscarPorId(Long id) { return repo.findById(id); }

    public Matricula salvar(Matricula m) {
        if (m.getAluno() != null && m.getDisciplina() != null) {

            boolean existeCursando = repo.existsByAlunoAndDisciplinaAndSituacao(
                    m.getAluno(), m.getDisciplina(), SituacaoMatricula.CURSANDO);

            boolean existeAprovado = repo.existsByAlunoAndDisciplinaAndSituacao(
                    m.getAluno(), m.getDisciplina(), SituacaoMatricula.APROVADO);

            boolean existeTrancado = repo.existsByAlunoAndDisciplinaAndSituacao(
                    m.getAluno(), m.getDisciplina(), SituacaoMatricula.TRANCADO);

            // Só valida duplicidade para novos registros
            boolean isNovo = (m.getId() == null);

            if (isNovo && existeCursando) {
                throw new MatriculaDuplicadaException(
                        "Aluno já está matriculado nesta disciplina (CURSANDO)."
                );
            }

            if (isNovo && existeAprovado) {
                throw new MatriculaDuplicadaException(
                        "Aluno já foi aprovado nesta disciplina e não pode cursá-la novamente."
                );
            }

            if (isNovo && existeTrancado) {
                throw new MatriculaDuplicadaException(
                        "Aluno já está matriculado nesta disciplina (TRANCADO)."
                );
            }
        }
        return repo.save(m);
    }

    public void excluir(Long id) { repo.deleteById(id); }
}