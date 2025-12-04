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
        // valida duplicata: não permitir matrícula CURSANDO duplicada
        if (m.getAluno() != null && m.getDisciplina() != null) {
            boolean existe = repo.existsByAlunoAndDisciplinaAndSituacao(
                    m.getAluno(), m.getDisciplina(), SituacaoMatricula.CURSANDO);
            if (existe && (m.getId() == null)) { // se é novo registro
                throw new MatriculaDuplicadaException("Aluno já matriculado nesta disciplina (CURSANDO).");
            }
        }
        return repo.save(m);
    }

    public void excluir(Long id) { repo.deleteById(id); }
}