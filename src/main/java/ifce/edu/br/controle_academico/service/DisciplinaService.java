package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.repository.DisciplinaRepository;
import ifce.edu.br.controle_academico.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository repo;

    public DisciplinaService(DisciplinaRepository repo) { this.repo = repo; }

    public List<Disciplina> listarTodas() { return repo.findAll(); }

    public Optional<Disciplina> buscarPorId(Long id) { return repo.findById(id); }

    public Disciplina salvar(Disciplina d) {
        if (d.getCodigo() != null && repo.existsByCodigo(d.getCodigo())) {
            throw new DuplicateResourceException("Código de disciplina já existe: " + d.getCodigo());
        }
        return repo.save(d);
    }

    public void excluir(Long id) { repo.deleteById(id); }
}